package com.dondoc.backend.moim.service;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.moim.dto.MoimCreateDto;
import com.dondoc.backend.moim.dto.MoimDetailDto;
import com.dondoc.backend.moim.dto.MoimInviteDto;
import com.dondoc.backend.moim.dto.MoimInviteListDto;
import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.entity.MoimMember;
import com.dondoc.backend.moim.repository.MoimMemberRepository;
import com.dondoc.backend.moim.repository.MoimRepository;
import com.dondoc.backend.user.entity.Account;
import com.dondoc.backend.user.entity.User;
import com.dondoc.backend.user.repository.AccountRepository;
import com.dondoc.backend.user.repository.UserRepository;
import com.dondoc.backend.user.service.AccountService;
import com.dondoc.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class MoimMemberServiceImpl implements MoimMemberService {

    private final MoimMemberRepository moimMemberRepository;
    private final AccountService accountService;
    private final UserService userService;
    private final MoimRepository moimRepository;

    // 모임 생성시 초대된 사람의 MoimMember 생성 함수
    @Transactional
    @Override
    public MoimMember createMoimMember(User inviter,User invitee,Moim moim, LocalDateTime signedAt){
        MoimMember moimMember = new MoimMember(0,0,signedAt,inviter.getName());
        moimMember.setUser(invitee);
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
    public int inviteMoimMember(Moim moim, List<MoimInviteDto.InviteDto> inviteList,Long inviterId) {

        int cnt = 0;
        int userType = 1;
        int moimType = moim.getMoimType();



        if(moimType==3) { // 모임 타입이 3인경우 모든 유저는 관리자
            userType=0;
        }

        for (MoimInviteDto.InviteDto inviteDto : inviteList) {
            Long userId = inviteDto.getUserId();

            // 모임에 이미 초대된 사용자인지 확인
            // userId와 moimId가 일치하는 사람이 있으면 초대할 수 없음
            Optional<MoimMember> byUserIdAndMoimId = moimMemberRepository.findByUser_IdAndMoim_Id(userId, moim.getId());

            if(byUserIdAndMoimId.isPresent()){
                log.info("이미 초대된 사용자 입니다. userId : {}",userId);
                continue;
            }

            User user = userService.findById(userId);
            User inviteUser = userService.findById(inviterId);

            MoimMember moimMember = new MoimMember(userType, 0,inviteUser.getName());
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

    @Override
    public MoimMember checkCanInvite(Long userId, Long moimId) throws Exception{
        List<MoimMember> moimMembers = moimMemberRepository.findMoimMember(userId, moimId);

        if(moimMembers.size()==0 || moimMembers.size()>1)
            throw new RuntimeException("userId가 " + userId+"인 사용자는 "+ "moimId가 " + moimId+ "인 모임에 존재하지 않습니다");
        // 관리자만 모임에 사용자를 초대할 수 있음
        if(moimMembers.get(0).getUserType()!=0){
            throw new RuntimeException("userId가 " + userId+"인 사용자는 "+ "moimId가 " + moimId+ "인 모임에서 초대할 권한이 없습니다");
        }
        return moimMembers.get(0);

    }

    public MoimMember findWithMoimAndUser(Long moimMemberId){
        List<MoimMember> withMoimAndUser = moimMemberRepository.findWithMoimAndUser(moimMemberId);
        if(withMoimAndUser.size()==0){
            throw new NotFoundException("id가 " + moimMemberId + "인 모임멤버가 존재하지 않습니다.");
        }

        if(withMoimAndUser.size()>1){
            throw new NotFoundException("id가 " + moimMemberId + "인 모임멤버 조회에 실패했습니다.");
        }

        return withMoimAndUser.get(0);
    }
    @Transactional
    @Override
    public int acceptMoimMember(Long moimMemberId, Long accountId, Long userId) throws Exception{

        int flag = 0;
        Account account = accountService.findByAccountId(accountId);
        System.out.println("findWithMoinMember fetch join 시작");
        MoimMember moimMember = this.findWithMoimAndUser(moimMemberId);
        Moim moim = moimMember.getMoim();
        User user = moimMember.getUser();

        int moimType = moim.getMoimType();

        if(user.getId() != userId){
            throw new NotFoundException("MoimMeberId가 " + moimMemberId + "인 모임멤버에 userId가 " + userId +"인 user가 존재하지 않습니다.");
        }

        if(account.getUser().getId()!=userId){
            throw new NotFoundException("userId가 " + userId + "인 사용자는 accountId가 " + accountId + "인 account를 가지고 있지 않습니다");
        }
        if(moimMember.getStatus()==1){
            throw new NotFoundException("이미 모임초대에 승인한 사용자 입니다");
        }


        if(moimType==2){ // 모입타입이 2인 모임의 경우 모임 활성화
            moim.changeIsActive(1);
            flag = 1;
        }

        moimMember.changeStatus(1);
        moimMember.changeAccount(account);
        return flag;

    }

    @Override
    public List<MoimInviteListDto.Response> findInviteList(Long userId) {
        List<MoimInviteListDto.Response> result = new ArrayList<>();
        List<MoimMember> inviteList = moimMemberRepository.findInviteList(userId);

        for (MoimMember moimMember : inviteList) {
            Moim moim = moimMember.getMoim();
            int type = moim.getMoimType();
            String moimType = new String();
            if(type==1){
                moimType="한명계좌";
            }else if(type==2){
                moimType="두명계좌";
            }else if(type==3){
                moimType="세명계좌";
            }

            MoimInviteListDto.Response response = new MoimInviteListDto.Response(moim.getId(),moimMember.getId(),moim.getMoimName(),
                    moimType,moim.getIntroduce(),moimMember.getInviterName());
            result.add(response);
        }

        return result;
    }

    @Transactional
    @Override
    public int deleteMoimMember(Long moimMemberid, Long userId) throws Exception{
        // moimMember를 삭제함과 동시에 양방향 연관관계를 고려하여 삭제해야 함
        // @ManyToOne User, @ManyToOne Moim
        int flag = 0;
        MoimMember moimMember = this.findWithMoimAndUser(moimMemberid);
        Moim moim = moimMember.getMoim();
        User user = moimMember.getUser();

        if(user.getId() != userId){
            throw new NotFoundException("MoimMeberId가 " + moimMember.getId() + "인 모임멤버에 userId가 " + userId +"인 user가 존재하지 않습니다.");
        }

        moimMemberRepository.delete(moimMember);

        if(moim!=null){
            moim.getMoimMemberList().remove(moimMember);
        }

        if(user!=null){
            user.getMoimMemberList().remove(moimMember);
        }

        if(moim.getMoimType()==2 && moimMember.getUserType()==0){ // 모임 타입이 2인 모임에 관리자가 초대를 거절하면 모임도 삭제 해야 함
            moimRepository.delete(moim);
            flag = 1;
        }

        return flag;
    }

    @Override
    public int checkUserType(Long userId, String moimIdentificationNumber) {
        List<MoimMember> moimMembers = moimMemberRepository.findByUserIdAndMoimINumber(userId, moimIdentificationNumber);
        if(moimMembers.size()==0 || moimMembers.size() > 1){
            throw new NotFoundException("모임멤버 조회 실패");
        }

        MoimMember moimMember = moimMembers.get(0);

        return moimMember.getUserType();
    }

    @Override
    public void checkMyAccount(Long userId, String memberAccountNumber) {
        Account byAccountNumber = accountService.findByAccountNumber(memberAccountNumber);
        if(byAccountNumber.getUser().getId()!=userId){
            throw new NotFoundException("일반사용자는 다른 사용자의 마이데이터를 볼 수 없습니다.");
        }
    }

    @Override
    public MoimMember findWithMoimId(Long moimId, Long moimMemberId) {
        MoimMember moimMember = moimMemberRepository.findWithMoimId(moimId, moimMemberId).orElseThrow(() -> new NotFoundException("moimId가 " + moimId + "인 모임에 moimMemberId가 " +
                moimMemberId + "인 moimMember가 없습니다."));
        return moimMember;
    }

}
