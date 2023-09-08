package com.bank.backend.dto;

import com.bank.backend.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

public class AccountCertificationDto {

    @Data
    @Builder
    public static class Request{
        private Long bankCode;
        private String accountNumber;
    }

    @Data
    @Builder
    public static class Response{
        private String accountNumber;
        private String ownerName;
        private String bankName;
        private String msg;
        @JsonIgnore
        private boolean success;
    }

}
