package com.bank.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class TransferDto {
    @ApiModel(value = "계좌이체", description = "계좌 이체릃 하기 위한 Request Dto")
    @Data
    @Builder
    public static class Request{
        @ApiModelProperty(value = "식별번호", example = "01026807453")
        private String identificationNumber;

        @ApiModelProperty(value = "내 계좌")
        private Long accountId;

        @ApiModelProperty(value = "상대 은행 코드")
        private Long toCode;

        @ApiModelProperty(value = "상대 계좌 번호")
        private String toAccount;

        @ApiModelProperty(value = "송금 금액")
        private Integer transferAmount;

        @ApiModelProperty(value = "계좌 비밀번호 4자리")
        private String password;

        @ApiModelProperty(value = "내가 보는 메모", example = "내 계좌에 남는 메모")
        private String memo;

        @ApiModelProperty(value = "상대가 보는 메모", example = "상대 계좌에 남는 메모")
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
