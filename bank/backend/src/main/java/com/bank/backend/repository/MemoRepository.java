package com.bank.backend.repository;

import com.bank.backend.entity.History;
import com.bank.backend.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    Optional<Memo> findByHistoryId(History history);

}
