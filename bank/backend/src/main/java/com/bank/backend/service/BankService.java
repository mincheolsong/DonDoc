package com.bank.backend.service;

import com.bank.backend.dto.AccountDetailResponseDto;
import com.bank.backend.dto.AccountListResponseDto;
import com.bank.backend.dto.HistoryDto;
import com.bank.backend.entity.History;

import java.util.List;
import com.bank.backend.dto.AccountDto;
import com.bank.backend.dto.TransferDto;

import java.util.Map;


public interface BankService {

    public int findAccountList(List<AccountListResponseDto> result, String number) throws Exception;

    public AccountDetailResponseDto findByAccountId(Long accountId) throws Exception;

    public boolean certification(String identificationNumber) throws Exception;

    public void createOwner(Map<String, String> info);

    public void createAccount(Map<String, String> info);

    public boolean countAccount(String identification);

    /** 계좌 거래 내역 조회 */
    List<History> getHistoryList(HistoryDto.Request req) throws Exception;

    /** 상세 거래 내역 조회 */
    History getDetailHistory(HistoryDto.Request req) throws Exception;

    public AccountDto getAccount(Map<String, String> info);

    public TransferDto transfer(Map<String, String> info);
}
