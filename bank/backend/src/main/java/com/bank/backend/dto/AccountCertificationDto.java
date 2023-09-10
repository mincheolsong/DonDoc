package com.bank.backend.dto;

import com.bank.backend.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

public class AccountCertificationDto {

    @ApiModel(value = "계좌 실명 조회", description = "계좌 실명 조회에 필요한 Request Dto")
    @Data
    @Builder
    public static class Request{
        @ApiModelProperty(value = "은행 코드")
        private Long bankCode;

        @ApiModelProperty(value = "계좌 번호")
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
