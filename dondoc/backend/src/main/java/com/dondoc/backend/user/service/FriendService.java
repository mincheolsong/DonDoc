package com.dondoc.backend.user.service;

import com.dondoc.backend.user.dto.friend.FriendListDto;
import com.dondoc.backend.user.dto.friend.FriendRequestDto;
import com.dondoc.backend.user.dto.friend.FriendSearchDto;
import org.springframework.stereotype.Service;

@Service
public interface FriendService {
    FriendListDto.Response friendList(Long userId);

    FriendRequestDto.RequestListResponse receiveList(Long userId);
    FriendRequestDto.RequestListResponse sendList(Long userId);

    FriendRequestDto.Response requestDeny(Long userId, String requestId);

    FriendRequestDto.Response requestAccess(Long userId, String requestId);

    FriendRequestDto.Response friendRequest(Long req, Long userId);

    FriendRequestDto.Response requestDelete(Long userId, Long requestId);

    FriendRequestDto.Response friendDelete(Long userId, String friendId);

    FriendSearchDto.Response searchFriend(Long userId, String phoneNumber);
}
