package com.bank.backend.service;

import com.bank.backend.dto.AccountDetailResponseDto;
import com.bank.backend.dto.AccountListResponseDto;
import com.bank.backend.entity.Account;

import java.util.List;

public interface BankService {

    public int findAccountList(List<AccountListResponseDto> result, String number) throws Exception;
    public AccountDetailResponseDto findByAccountId(Long accountId) throws Exception;
}
