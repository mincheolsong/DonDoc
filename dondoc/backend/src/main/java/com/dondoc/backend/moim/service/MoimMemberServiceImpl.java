package com.dondoc.backend.moim.service;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.moim.dto.MoimCreateDto;
import com.dondoc.backend.moim.dto.MoimDetailDto;
import com.dondoc.backend.moim.dto.MoimInviteDto;
import com.dondoc.backend.moim.dto.MoimInviteListDto;
import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.entity.MoimMember;
import com.dondoc.backend.moim.repository.MoimMemberRepository;
import com.dondoc.backend.user.entity.Account;
import com.dondoc.backend.user.entity.User;
import com.dondoc.backend.user.repository.AccountRepository;
import com.dondoc.backend.user.repository.UserRepository;
import com.dondoc.backend.user.service.AccountService;
import com.dondoc.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MoimMemberServiceImpl implements MoimMemberService {

    private final MoimMemberRepository moimMemberRepository;
    private final AccountService accountService;
    private final UserService userService;

    // 모임 생성시 초대된 사람의 MoimMember 생성 함수
    @Transactional
    @Override
    public MoimMember createMoimMember(User user,Moim moim, LocalDateTime signedAt){
        MoimMember moimMember = new MoimMember(0,0,signedAt);
        moimMember.setUser(user);
        moimMember.setMoim(moim);
        moimMemberRepository.save(moimMember);
        return moimMember;
    }

    // 모임 생성한 사람의 MoimMember 생성 함수
    @Transactional
    @Override
    public MoimMember createMoimCreatorMember(User user, Moim moim, LocalDateTime signedAt, Account account) {


        // 모임에 대한 승인(status == 1)이 된 상태로 생성
        MoimMember moimMember = new MoimMember(0,1,signedAt,account);
        moimMember.setUser(user);
        moimMember.setMoim(moim);
        moimMemberRepository.save(moimMember);


        return moimMember;
    }

    @Override
    public MoimMember findById(Long id) {
        MoimMember moimMember = moimMemberRepository.findById(id).orElseThrow(() -> new NotFoundException("id가 " + id + "인 moimMember가 없습니다."));

        return moimMember;
    }

    /**
     * 모임 회원초대할 때 사용하는 inviteMoimMember함수
     */
    @Transactional
    @Override
    public int inviteMoimMember(Moim moim, List<MoimInviteDto.InviteDto> inviteList) {

        int cnt = 0;
        int userType = 1;
        int moimType = moim.getMoimType();

        if(moimType==3) { // 모임 타입이 3인경우 모든 유저는 관리자
            userType=0;
        }

        for (MoimInviteDto.InviteDto inviteDto : inviteList) {
            Long userId = inviteDto.getUserId();
            User user = userService.findById(userId);


            MoimMember moimMember = new MoimMember(userType, 0);
            moimMember.setUser(user);
            moimMember.setMoim(moim);

            moimMemberRepository.save(moimMember);
            cnt += 1;
        }

        return cnt;
    }

    @Override
    public MoimMember findMoimMember(Long userId, Long moimId) throws Exception{
        List<MoimMember> moimMembers = moimMemberRepository.findMoimMember(userId, moimId);
        if(moimMembers.size()==0 || moimMembers.size()>1)
            throw new RuntimeException("userId가 " + userId+"인 사용자는 "+ "moimId가 " + moimId+ "인 모임에 존재하지 않습니다");

        return moimMembers.get(0);

    }

    @Transactional
    @Override
    public void acceptMoimMember(Long moimMemberId, Long accountId, Long userId) throws Exception{

        Account account = accountService.findByAccountId(accountId);
        MoimMember moimMember = this.findById(moimMemberId);
        int moimType = moimMember.getMoim().getMoimType();

        if(account.getUser().getId()!=userId){
            throw new NotFoundException("userId가 " + userId + "인 사용자는 accountId가 " + accountId + "인 account를 가지고 있지 않습니다");
        }
        if(moimMember.getStatus()==1){
            throw new NotFoundException("이미 모임초대에 승인한 사용자 입니다");
        }


        if(moimType==2){ // 모입타입이 2인 모임멤버가 초대를 승인한 경우, moim의 비활성화를 해제해야 함

        }

        moimMember.changeStatus(1);
        moimMember.changeAccount(account);

    }

    @Override
    public List<MoimInviteListDto.Response> findInviteList(Long userId) {
        List<MoimInviteListDto.Response> result = new ArrayList<>();
        List<MoimMember> inviteList = moimMemberRepository.findInviteList(userId);

        for (MoimMember moimMember : inviteList) {
            MoimInviteListDto.Response response = new MoimInviteListDto.Response(moimMember.getMoim().getId(),moimMember.getId());
            result.add(response);
        }

        return result;
    }

/*    @Transactional
    @Override
    public void deleteMoimMember(MoimMember moimMember) throws Exception{
        // moimMember를 삭제함과 동시에 양방향 연관관계를 고려하여 삭제해야 함
        // @ManyToOne User, @ManyToOne Moim
        Moim moim = moimMember.getMoim();
        if(moim!=null){
            moim.getMoimMemberList().remove(moimMember);
        }
        User user = moimMember.getUser();
        if(user!=null){
            user.getMoimMemberList().remove(moimMember);
        }
        moimMemberRepository.delete(moimMember);
    }*/

}
