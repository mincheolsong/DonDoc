package com.bank.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

public class PasswordDto {

    @ApiModel(value = "비밀번호 재설정", description = "비밀번호 재설정 Request Dto")
    @Data
    @Builder
    public static class Request{
        @ApiModelProperty(value = "식별번호")
        private String identificationNumber;

        @ApiModelProperty(value = "은행 코드")
        private Long bankCode;

        @ApiModelProperty(value = "계좌 번호")
        private String accountNumber;

        @ApiModelProperty(value = "새 비밀번호")
        private String newPassword;
    }

    @Data
    @Builder
    public static class Response{
        private String msg;

        @JsonIgnore
        private boolean success;
    }
}
