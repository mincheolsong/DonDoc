package com.dondoc.backend.user.service;

import com.dondoc.backend.user.dto.friend.FriendListDto;
import com.dondoc.backend.user.dto.friend.FriendRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface FriendService {
    FriendListDto.Response friendList(Long userId);

    FriendListDto.Response requestList(Long userId);

    FriendRequestDto.Response requestDeny(Long userId, String requestId);

    FriendRequestDto.Response requestAccess(Long userId, String requestId);

    FriendRequestDto.Response friendRequest(Long req, Long userId);
}
