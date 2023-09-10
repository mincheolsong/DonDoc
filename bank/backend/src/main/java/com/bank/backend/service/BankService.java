package com.bank.backend.service;

import com.bank.backend.dto.*;
import com.bank.backend.entity.History;
import com.bank.backend.entity.Owner;

import java.util.List;

import java.util.Map;


public interface BankService {

    public int findAccountList(List<AccountListDto.Response> result, String number) throws Exception;

    public AccountDetailDto.Response findByAccountId(Long accountId) throws Exception;

    /** 계좌 거래 내역 조회 */
    public boolean countAccount(String identification);

    public List<History> getHistoryList(HistoryDto.Request req) throws Exception;

    public History getDetailHistory(HistoryDto.Request req) throws Exception;

    public OwnerDto.Response certification(OwnerDto.Request request) throws Exception;

    public OwnerDto.Response certification(AccountDto.Request request) throws Exception;

    public AccountCertificationDto.Response getAccount(AccountCertificationDto.Request request);

    public TransferDto.Response transfer(TransferDto.Request request) throws Exception;

    public void createOwner(OwnerDto.Response response);

    public AccountDto.Response createAccount(Owner owner, AccountDto.Request request) throws Exception;

    public
}
