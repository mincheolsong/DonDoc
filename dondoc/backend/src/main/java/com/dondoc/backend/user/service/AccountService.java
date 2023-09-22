package com.dondoc.backend.user.service;

import com.dondoc.backend.user.entity.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService{
    Account findById(Long accountId);
}
