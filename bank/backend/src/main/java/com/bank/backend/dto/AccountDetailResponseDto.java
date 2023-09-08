package com.bank.backend.dto;

import com.bank.backend.entity.Account;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDetailResponseDto {

    private Long accountId; // ID
    private String accountName; // 계좌이름
    private String accountNumber; // 계좌번호
    private int balance; // 잔액
    private Long bankCode; // 은행코드
    private String bankName; // 은행이름

    public static AccountDetailResponseDto toDTO(Account entity){
        return AccountDetailResponseDto.builder()
                .accountId(entity.getAccountId())
                .accountName(entity.getAccountName())
                .accountNumber(entity.getAccountNumber())
                .balance(entity.getBalance())
                .bankCode(entity.getBankCode().getBankCodeId())
                .bankName(entity.getBankCode().getBankName())
                .build();
    }
}
