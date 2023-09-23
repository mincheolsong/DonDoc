package com.dondoc.backend.user.service;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.user.dto.friend.FriendListDto;
import com.dondoc.backend.user.dto.friend.FriendRequestDto;
import com.dondoc.backend.user.entity.Friend;
import com.dondoc.backend.user.entity.User;
import com.dondoc.backend.user.repository.FriendRepository;
import com.dondoc.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class FriendServiceImpl implements FriendService{

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public FriendServiceImpl(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    // 친구 요청 보내기
    @Override
    @Transactional
    public FriendRequestDto.Response friendRequest(Long friendId, Long userId) {
        if(friendId == userId){
            throw new NoSuchElementException("자신에게 요청을 보낼 수 없습니다.");
        }
        if(friendRepository.findByUserIdAndFriendId(userId, friendId).isPresent()){
            throw new NoSuchElementException("이미 존재한 요청 또는 친구입니다.");
        }
        if(friendRepository.findByFriendIdAndUserId(userId, friendId).isPresent()){
            throw new NoSuchElementException("이미 존재한 요청 또는 친구입니다.");
        }
        // 내 정보
        User me = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        // 보내는 요청
        Friend sendRequest = Friend.builder()
                .friendId(friendId)
                .user(me)
                .build();

        friendRepository.save(sendRequest);

        return FriendRequestDto.Response.builder()
                .msg("요청이 완료되었습니다.")
                .success(true)
                .build();
    }

    // 요청 승인
    @Override
    @Transactional
    public FriendRequestDto.Response requestAccess(Long userId, String requestId) {
        Friend friend = friendRepository.findByIdAndStatus(Long.parseLong(requestId), 0)
                .orElseThrow(() -> new NotFoundException("요청을 찾을 수 없습니다."));

        if(friend.getFriendId() != userId){
            throw new NoSuchElementException("요청에 접근 권한이 없습니다.");
        }

        // 상태 변경
        friend.setStatus(1);

        friendRepository.save(friend);

        return FriendRequestDto.Response.builder()
                .msg("수락이 완료되었습니다.")
                .success(true)
                .build();
    }

    // 요청 거절
    @Override
    @Transactional
    public FriendRequestDto.Response requestDeny(Long userId, String requestId) {
        Friend friend = friendRepository.findById(Long.parseLong(requestId))
                .orElseThrow(() -> new NotFoundException("요청을 찾을 수 없습니다."));

        if(friend.getFriendId() != userId){
            throw new NoSuchElementException("요청에 접근 권한이 없습니다.");
        }

        friendRepository.delete(friend);

        return FriendRequestDto.Response.builder()
                .msg("거절이 완료되었습니다.")
                .success(true)
                .build();
    }

    // 받은 요청 목록
    @Override
    public FriendListDto.Response receiveList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        List<Friend> list = friendRepository.findByFriendIdAndStatus(userId, 0);

        return FriendListDto.Response.builder()
                .list(list)
                .msg("친구 요청 목록을 불러왔습니다.")
                .success(true)
                .build();
    }

    // 보낸 요청 목록
    @Override
    public FriendListDto.Response requestList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        List<Friend> list = friendRepository.findByUserAndStatus(user, 0);

        return FriendListDto.Response.builder()
                .list(list)
                .msg("친구 요청 목록을 불러왔습니다.")
                .success(true)
                .build();
    }

    // 친구 목록
    @Override
    public FriendListDto.Response friendList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저를 찾을 수 없습니다."));

        List<Friend> list = friendRepository.findByUserAndStatus(user, 1);

        return FriendListDto.Response.builder()
                .list(list)
                .msg("친구 목록을 불러왔습니다.")
                .success(true)
                .build();
    }

    //

}
