package com.dondoc.backend.user.repository;

import com.dondoc.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 핸드폰 번호로 유저 정보 찾는 메서드
    Optional<User> findByPhoneNumber(String phoneNumber);
}
