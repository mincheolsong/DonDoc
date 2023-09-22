package com.dondoc.backend.user.service;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.user.entity.Account;
import com.dondoc.backend.user.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private final AccountRepository accountRepository;

    @Override
    public Account findById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("계좌를 찾을 수 없습니다."));
    }
}
