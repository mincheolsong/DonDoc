package com.dondoc.backend.user.dto.account;

import com.dondoc.backend.user.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public class AccountDto {

    @Data
    @Builder
    public static class Response{
        private String msg;
        @JsonIgnore
        private boolean success;
    }
}
