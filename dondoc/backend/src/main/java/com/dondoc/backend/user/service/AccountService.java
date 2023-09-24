package com.dondoc.backend.user.service;

import com.dondoc.backend.user.dto.account.AccountListDto;
import com.dondoc.backend.user.dto.account.HistoryDto;
import com.dondoc.backend.user.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService{
    Account findById(Long accountId);

    AccountListDto.Response loadBankList(Long userId);
    AccountListDto.Response loadList(Long userId);

    AccountListDto.Response saveList(Long userId, List<AccountListDto.Request> accountList);

    AccountListDto.Response searchHistory(Long userId, HistoryDto historyDto);
}
