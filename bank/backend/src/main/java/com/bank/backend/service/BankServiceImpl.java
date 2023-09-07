package com.bank.backend.service;

import com.bank.backend.repository.AccountRepository;
import com.bank.backend.repository.BankCodeRepository;
import com.bank.backend.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository;
    private final BankCodeRepository bankCodeRepository;
    private final HistoryRepository historyRepository;


}
