package com.bank.backend.repository;

import com.bank.backend.entity.Account;
import com.bank.backend.entity.BankCode;
import com.bank.backend.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByOwner(Owner owner);

    Optional<Account> findByOwnerAndAccountNumber(Owner owner, String accountNumber);

    Optional<Account> findByBankCode_IdAndAccountNumber(Long bankCode, String accountNumber);

    Optional<Account> findByAccountNumber(String accountNumber);
}
