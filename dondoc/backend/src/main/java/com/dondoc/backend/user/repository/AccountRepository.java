package com.dondoc.backend.user.repository;

import com.dondoc.backend.user.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
