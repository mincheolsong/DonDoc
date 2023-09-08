package com.bank.backend.dto;

import com.bank.backend.entity.Owner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


public class AccountDto {

    @Data
    @Builder
    public static class Request{
        private String identificationNumber;
        private String accountName;
        private String bankCode;
        private String password;
    }

    @Data
    @Builder
    public static class Response{
        private String msg;
        @JsonIgnore
        private boolean success;
    }
}
