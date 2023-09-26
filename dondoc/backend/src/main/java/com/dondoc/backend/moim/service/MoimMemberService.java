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

    int createMoimMember(User user, Moim moim, LocalDateTime signedAt, Account account, List<MoimCreateDto.InviteDto> manager);

    int inviteMoimMember(Moim moim, List<MoimInviteDto.InviteDto> inviteList);

    MoimMember findMoimMember(Long userId, Long moimId) throws Exception;

    void acceptMoimMember(Long moimMemberId, Long accountId, Long userId) throws Exception;

    void deleteMoimMember(MoimMember moimMember) throws Exception;

}
