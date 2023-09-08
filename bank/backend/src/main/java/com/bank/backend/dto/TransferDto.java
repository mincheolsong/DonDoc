package com.bank.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

public class TransferDto {

    @Data
    @Builder
    public static class Request{
        private String identificationNumber;
        private Long accountId;
        private Long toCode;
        private String toAccount;
        private Integer transferAmount;
        private String password;
        private String memo;
        private String toMemo;
    }

    @Data
    @Builder
    public static class Response{
        private String msg;
        @JsonIgnore
        private boolean success;
    }
}
