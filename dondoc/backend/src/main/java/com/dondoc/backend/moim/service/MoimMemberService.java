package com.dondoc.backend.moim.service;

import com.dondoc.backend.moim.dto.MoimCreateDto;
import com.dondoc.backend.moim.dto.MoimDetailDto;
import com.dondoc.backend.moim.dto.MoimInviteDto;
import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.entity.MoimMember;
import com.dondoc.backend.user.entity.Account;
import com.dondoc.backend.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface MoimMemberService {

    MoimMember createMoimMember(User user,Moim moim, LocalDateTime signedAt);
    MoimMember createMoimCreatorMember(User user, Moim moim, LocalDateTime signedAt, Account account);

    MoimMember findById(Long id);
    int inviteMoimMember(int moimType, Moim moim, List<MoimInviteDto.InviteDto> inviteList);

    MoimMember findMoimMember(Long userId, Long moimId) throws Exception;

    void acceptMoimMember(Long moimMemberId, Long accountId, Long userId) throws Exception;

//    void deleteMoimMember(Long moimMemberId) throws Exception;

}
