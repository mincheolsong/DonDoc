package com.bank.backend.repository;

import com.bank.backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {


}
