package com.dondoc.backend.user.repository;

import com.dondoc.backend.user.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountId(Long accountId);
    List<Account> findByUserId(Long userId);
    void deleteByAccountId(Long accountId);

    List<Account> findByAccountNumber(String accountNumber);
}
