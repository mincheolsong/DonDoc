package com.bank.backend.repository;

import com.bank.backend.entity.Account;
import com.bank.backend.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByAccount(Account account);
    Optional<History> findByAccountAndId(Account account, Long historyId);

}
