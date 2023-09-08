package com.bank.backend.service;

import com.bank.backend.dto.*;
import com.bank.backend.entity.History;

import java.util.List;

import java.util.Map;


public interface BankService {

    public int findAccountList(List<AccountListDto.Response> result, String number) throws Exception;

    public AccountDetailDto.Response findByAccountId(Long accountId) throws Exception;

    public boolean certification(String identificationNumber) throws Exception;

    public void createOwner(Map<String, String> info);

    public void createAccount(Map<String, String> info);

    public boolean countAccount(String identification);

    public List<History> getHistoryList(HistoryDto.Request req) throws Exception;

    public History getDetailHistory(HistoryDto.Request req) throws Exception;

    public AccountDto getAccount(Map<String, String> info);

    public TransferDto transfer(Map<String, String> info);
}
