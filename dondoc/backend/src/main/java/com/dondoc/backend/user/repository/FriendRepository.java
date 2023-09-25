package com.dondoc.backend.user.repository;

import com.dondoc.backend.user.entity.Friend;
import com.dondoc.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByUserAndStatus(User user, int status);
    List<Friend> findByFriendIdAndStatus(Long friendId, int status);
    Optional<Friend> findByIdAndStatus(Long Id, int status);
    Optional<Friend> findByUserIdAndFriendId(Long userId, Long friendId);
    Optional<Friend> findByFriendIdAndUserId(Long userId, Long friendId);
    List<Friend> findByUserOrFriendIdAndStatus(User user, Long friendId, int status);
}
