package com.dondoc.backend.user.repository;

import com.dondoc.backend.user.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {

}
