package com.dondoc.backend.user.dto.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

public class AccountCertificationDto {

    @Data
    @Builder
    @ApiModel(value = "계좌 실명 조회", description = "계좌 실명 조회 Dto")
    public static class Request{
        @ApiModelProperty(value = "계좌 번호", example = "5499039847363")
        private String accountNumber;
        @ApiModelProperty(value = "은행 코드", example = "31")
        private Long bankCode;
    }

    @Data
    @Builder
    public static class Response{
        private String msg;
        private Map response;
        private boolean success;
    }
}
