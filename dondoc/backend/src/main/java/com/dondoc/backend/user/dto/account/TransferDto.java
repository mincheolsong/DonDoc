package com.dondoc.backend.user.dto.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

public class TransferDto {

    @Data
    @Builder
    @ApiModel(value = "계좌 이체", description = "메모 작성 API")
    public static class Request{
        @ApiModelProperty(value = "계좌 ID", example = "6")
        private Long accountId;

        @ApiModelProperty(value = "계좌 비밀번호", example = "1234")
        private String password;

        @ApiModelProperty(value = "내 거래 내역 표시", example = "내 계좌에 남는 표시")
        private String sign;

        @ApiModelProperty(value = "상대 거래 내역 표시", example = "상대 계좌에 남는 표시")
        private String toSign;

        @ApiModelProperty(value = "상대 계좌", example = "8374837098285")
        private String toAccount;

        @ApiModelProperty(value = "상대 은행", example = "4")
        private String toCode;

        @ApiModelProperty(value = "거래 금액", example = "100000")
        private Integer transferAmount;
    }

    @Data
    @Builder
    public static class Response{
        private boolean success;
        private String msg;
    }

}
