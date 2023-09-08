package com.bank.backend.service;

import com.bank.backend.common.exception.NotFoundException;
import com.bank.backend.common.utils.EncryptionUtils;
import com.bank.backend.dto.AccountDetailResponseDto;
import com.bank.backend.dto.AccountListResponseDto;
import com.bank.backend.entity.Account;
import com.bank.backend.entity.Owner;
import com.bank.backend.repository.AccountRepository;
import com.bank.backend.repository.BankCodeRepository;
import com.bank.backend.repository.HistoryRepository;
import com.bank.backend.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository;
    private final BankCodeRepository bankCodeRepository;
    private final HistoryRepository historyRepository;
    private final OwnerRepository ownerRepository;

    @Value("${owner.salt}")
    private String salt;
    @Override
    public int findAccountList(List<AccountListResponseDto> result, String number) throws Exception{
        // number 해싱 후 조회
        String hashed_number = EncryptionUtils.encryption(number,salt);

        Owner owner = ownerRepository.findByIdentificationNumberWithAccount(hashed_number)
                .orElseThrow(()->new NotFoundException(number + " 에 해당하는 계좌정보가 존재하지 않습니다"));

        int cnt = 0;

        for (Account account : owner.getAccountList()) {
            AccountListResponseDto tmp = AccountListResponseDto.toDTO(account);
            result.add(tmp);
            cnt+=1;
        }

        return cnt; // 조회한 계좌목록 갯수 리턴

    }
    @Override
    public AccountDetailResponseDto findByAccountId(Long accountId) throws Exception {
        Account account = accountRepository.findById(accountId).orElseThrow(()->new NotFoundException(accountId + "를 ID로 가지는 계좌가 존재하지 않습니다"));

        return AccountDetailResponseDto.toDTO(account);

    }


}
