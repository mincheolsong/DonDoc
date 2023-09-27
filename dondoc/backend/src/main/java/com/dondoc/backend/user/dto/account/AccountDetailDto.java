package com.dondoc.backend.user.dto.account;

import com.dondoc.backend.user.entity.Account;
import lombok.Builder;
import lombok.Data;

public class AccountDetailDto {

    @Data
    @Builder
    public static class Response{
        private boolean success;
        private String msg;
        private AccountDetail accountDetail;
    }

    @Data
    @Builder
    public static class AccountDetail{
        private Long accountId; // ID
        private String accountName; // 계좌이름
        private String accountNumber; // 계좌번호
        private int balance; // 잔액
        private Long bankCode; // 은행코드
        private String bankName; // 은행이름
    }
}
