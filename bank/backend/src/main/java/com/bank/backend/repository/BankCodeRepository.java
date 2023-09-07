package com.bank.backend.repository;

import com.bank.backend.entity.BankCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankCodeRepository extends JpaRepository<BankCode, Long> {
}
