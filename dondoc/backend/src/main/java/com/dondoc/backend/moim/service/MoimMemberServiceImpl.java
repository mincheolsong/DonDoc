package com.dondoc.backend.moim.service;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.moim.dto.MoimCreateDto;
import com.dondoc.backend.moim.dto.MoimDetailDto;
import com.dondoc.backend.moim.dto.MoimInviteDto;
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
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MoimMemberServiceImpl implements MoimMemberService {

    private final MoimMemberRepository moimMemberRepository;
    private final AccountService accountService;
    private final UserService userService;

    /**
     * 모임 type이 2인 경우 사용하는 함수
     */
    @Transactional
    @Override
    public Long createMoimMember(User user, LocalDateTime signedAt, Long accountId, List<MoimCreateDto.InviteDto> manager) {
        Account account = accountService.findById(accountId);

        MoimMember moimMember = new MoimMember(0,1,signedAt,account); // 모임을 생성한 사람의 MoimMember
        moimMember.setUser(user);
        moimMemberRepository.save(moimMember);

        // 초대하는 관리자의 MoimMember 생성
        MoimCreateDto.InviteDto inviteDto = manager.get(0);
        Long userId = inviteDto.getUserId();
        User managerUser = userService.findById(userId);

        MoimMember managerMoimMember = new MoimMember(0,0,signedAt);
        managerMoimMember.setUser(managerUser);
        moimMemberRepository.save(managerMoimMember);

        return managerMoimMember.getId();
    }

    /**
     * 모임 type이 1, 3인 경우 사용하는 함수
     */
    @Transactional
    @Override
    public MoimMember createMoimMember(User user, Moim moim, LocalDateTime signedAt, Account account) {


        // 모임을 생성한 사람의 MoimMember 생성
        // 모임에 대한 승인(status == 1)이 된 상태로 생성
        MoimMember moimMember = new MoimMember(0,1,signedAt,account);
        moimMember.setUser(user);
        moimMember.setMoim(moim);
        moimMemberRepository.save(moimMember);

      /*  // 모임 생성 시 필요한 관리자의 MoimMember 생성
        // 모임에 대한 승인이 되지 않은(status == 0) 상태로 생성
        if(manager.size() > 0){
            for(MoimCreateDto.InviteDto InviteDto : manager){
                    moimMember = new MoimMember(0,0,signedAt); // 관리자, 아직 미승인 상태
                    User mUser = userRepository.findById(InviteDto.getUserId())
                            .orElseThrow(() -> new NotFoundException("유저의 정보를 찾을 수 없습니다."));
                    moimMember.setUser(mUser);
                    moimMember.setMoim(moim);
                    moimMemberRepository.save(moimMember);
                    cnt+=1;
            }
        }*/

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
    public int inviteMoimMember(int moimType, Moim moim, List<MoimInviteDto.InviteDto> inviteList) {

        int cnt = 0;
        int userType = 1;

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
    public void acceptMoimMember(MoimMember moimMember,Long accountId,Long userId) throws Exception{

        Account account = accountService.findById(accountId);
        if(account.getUser().getId()!=userId){
            throw new NotFoundException("userId가 " + userId + "인 사용자는 accountId가 " + accountId + "인 account를 가지고 있지 않습니다");
        }
        if(moimMember.getStatus()==1){
            throw new NotFoundException("이미 모임초대에 승인한 사용자 입니다");
        }


        moimMember.changeStatus(1);
        moimMember.changeAccount(account);
    }

    @Transactional
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
    }

}
