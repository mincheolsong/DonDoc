package com.bank.backend.service;

import com.bank.backend.dto.AccountDetailResponseDto;
import com.bank.backend.dto.AccountListResponseDto;
import com.bank.backend.entity.Account;
import java.util.Map;

import java.util.List;

public interface BankService {

    public int findAccountList(List<AccountListResponseDto> result, String number) throws Exception;

    public AccountDetailResponseDto findByAccountId(Long accountId) throws Exception;

    public boolean certification(String identificationNumber) throws Exception;

    public void createOwner(Map<String, String> info);

    public void createAccount(Map<String, String> info);

    public boolean countAccount(String identification);
}
