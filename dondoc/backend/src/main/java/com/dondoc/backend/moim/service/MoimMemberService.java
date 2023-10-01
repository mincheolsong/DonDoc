package com.dondoc.backend.moim.service;

import com.dondoc.backend.moim.dto.MoimCreateDto;
import com.dondoc.backend.moim.dto.MoimDetailDto;
import com.dondoc.backend.moim.dto.MoimInviteDto;
import com.dondoc.backend.moim.dto.MoimInviteListDto;
import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.entity.MoimMember;
import com.dondoc.backend.user.entity.Account;
import com.dondoc.backend.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface MoimMemberService {

    MoimMember createMoimMember(User inviter,User invitee,Moim moim, LocalDateTime signedAt);
    MoimMember createMoimCreatorMember(User user, Moim moim, LocalDateTime signedAt, Account account);

    MoimMember findById(Long id);
    int inviteMoimMember(Moim moim, List<MoimInviteDto.InviteDto> inviteList,Long inviterId);

    MoimMember findMoimMember(Long userId, Long moimId) throws Exception;

    MoimMember checkCanInvite(Long userId,Long moimId) throws Exception;

    int acceptMoimMember(Long moimMemberId, Long accountId, Long userId) throws Exception;

    List<MoimInviteListDto.Response> findInviteList(Long userId);

    int deleteMoimMember(Long moimMemberId, Long userId) throws Exception;

    int checkUserType(Long userId, String moimIdentificationNumber);

    void checkMyAccount(Long userId, String memberAccountNumber);

    MoimMember findWithMoimId(Long moimId, Long moimMemberId);
}
