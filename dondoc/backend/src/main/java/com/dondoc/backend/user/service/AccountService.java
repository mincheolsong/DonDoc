package com.dondoc.backend.user.service;

import com.dondoc.backend.user.dto.account.*;
import com.dondoc.backend.user.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService{

    Account findByAccountNumber(String accountNumber);

    Account findById(Long accountId);

    AccountListDto.BankResponse loadBankList(Long userId);
    AccountListDto.Response loadList(Long userId);

    AccountListDto.Response saveList(Long userId, List<AccountListDto.Request> accountList);

    HistoryDto.Response searchHistory(Long userId, String accountNumber);

    AccountDto.Response setAccount(Long userId, Long accountId);

    AccountDetailDto.Response accountDetail(Long accountId);

    HistoryDetailDto.Response historyDetail(Long userId, HistoryDetailDto.Request historyDto);

    HistoryMemoDto.Response historyMemo(Long userId, HistoryMemoDto.Request memo);

    TransferDto.Response transferAmount(Long userId, TransferDto.Request request);

    Account findByAccountId(Long accountId);
}
