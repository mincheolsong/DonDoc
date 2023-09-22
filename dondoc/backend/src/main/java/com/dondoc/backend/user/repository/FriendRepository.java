package com.dondoc.backend.user.repository;

import com.dondoc.backend.user.entity.Friend;
import com.dondoc.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByUserAndStatus(User user, int status);
}
