package com.dondoc.backend.user.dto.account;

import com.dondoc.backend.user.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public class AccountListDto {

    @Data
    @Builder
    @ApiModel(value = "계좌 리스트 등록", description = "계좌 등록 Dto")
    public static class Request{
        @ApiModelProperty(value = "계좌 ID ", example = "6")
        private Long accountId;

        @ApiModelProperty(value = "은행 코드", example = "89")
        private Long bankCode;

        @ApiModelProperty(value = "은행 이름", example = "케이뱅크")
        private String bankName;

        @ApiModelProperty(value = "계좌 번호", example = "5300334585926")
        private String accountNumber;
    }

    @Data
    @Builder
    public static class Response{
        private String msg;
        private List<AccountDetailDto.AccountDetail> accountList;
        @JsonIgnore
        private boolean success;
    }

    @Data
    @Builder
    public static class BankResponse{
        private String msg;
        private List<Account> accountList;
        @JsonIgnore
        private boolean success;
    }
}
