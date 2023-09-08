package com.bank.backend.service;

import com.bank.backend.dto.*;
import com.bank.backend.entity.History;
import com.bank.backend.entity.Owner;

import java.util.List;

import java.util.Map;


public interface BankService {

    public int findAccountList(List<AccountListResponseDto> result, String number) throws Exception;

    public AccountDetailResponseDto findByAccountId(Long accountId) throws Exception;

    /** 계좌 거래 내역 조회 */
    List<History> getHistoryList(HistoryDto.Request req) throws Exception;

    /** 상세 거래 내역 조회 */
    History getDetailHistory(HistoryDto.Request req) throws Exception;

    public OwnerCertificationDto.Response certification(OwnerDto.Request request) throws Exception;

    public OwnerCertificationDto.Response certification(AccountDto.Request request) throws Exception;

    public AccountCertificationDto.Response getAccount(AccountCertificationDto.Request request);

    public TransferDto.Response transfer(TransferDto.Request request) throws Exception;

    public void createOwner(OwnerCertificationDto.Response response);

    public AccountDto.Response createAccount(Owner owner, AccountDto.Request request) throws Exception;

    public boolean countAccount(String identification);
}
