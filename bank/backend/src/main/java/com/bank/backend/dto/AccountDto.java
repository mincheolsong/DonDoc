package com.bank.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;


public class AccountDto {
    @ApiModel(value="계좌 개설", description="계좌 개설에 필요한 Request Dto")
    @Data
    @Builder
    public static class Request{
        @ApiModelProperty(value = "식별번호", example = "01026807453")
        private String identificationNumber;

        @ApiModelProperty(value = "계좌이름 - 개인 계좌 : 이름, 모임 계좌 : 모임 이름")
        private String accountName;

        @ApiModelProperty(value = "은행 코드")
        private Long bankCode;

        @ApiModelProperty(value = "계좌 비밀번호(4자리)")
        private String password;
    }

    @Data
    @Builder
    public static class Response{
        private String msg;

        
        private String ownerName;

        
        private String accountNumber;

        
        private String bankName;

        @JsonIgnore
        private boolean success;
    }
}
