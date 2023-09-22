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
import com.dondoc.backend.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    /**
     * 모임 생성할 때 사용한는 MoimMember 생성함수
     */
    @Transactional
    @Override
    public int createMoimMember(User user, Moim moim, LocalDateTime signedAt, Account account, List<MoimCreateDto.InviteDto> manager) {

        int cnt = 1;
        // 모임을 생성한 사람의 MoimMember 생성
        // 모임에 대한 승인(status == 1)이 된 상태로 생성
        MoimMember moimMember = new MoimMember(0,1,signedAt,account);
        moimMember.setUser(user);
        moimMember.setMoim(moim);
        moimMemberRepository.save(moimMember);

        System.out.println("여기까지 실행 됨" + moimMember.getId());

        // 모임 생성 시 필요한 관리자의 MoimMember 생성
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
        }

        return cnt;
    }

    /**
     * 모임 회원초대할 때 사용하는 inviteMoimMember함수
     */
    @Transactional
    @Override
    public int inviteMoimMember(Moim moim, List<MoimInviteDto.InviteDto> inviteList) {

        int cnt = 0;

        for(MoimInviteDto.InviteDto inviteDto : inviteList){
            Long userId = inviteDto.getUserId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("유저의 정보를 찾을 수 없습니다."));

            MoimMember moimMember = new MoimMember(1,0);
            moimMember.setUser(user);
            moimMember.setMoim(moim);

            moimMemberRepository.save(moimMember);
            cnt+=1;
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
    public void acceptMoimMember(Long id) throws Exception{
        MoimMember moimMember = moimMemberRepository.findById(id).orElseThrow(()-> new NotFoundException("moimMemberId : " + id + "에 해당하는 moimMember가 존재하지 않습니다"));

        moimMember.changeStatus(1);
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
