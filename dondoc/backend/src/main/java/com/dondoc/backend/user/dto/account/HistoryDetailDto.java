package com.dondoc.backend.user.dto.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class HistoryDetailDto {
    @Data
    @Builder
    @ApiModel(value = "거래 내역 상세 조회", description = "거래 내역 상세 조회 API")
    public static class Request{
        @ApiModelProperty(value = "계좌번호", example = "5300334585926")
        private String accountNumber;

        @ApiModelProperty(value = "거래내역 ID", example = "4")
        private Long historyId;
    }

    @Data
    @Builder
    public static class Response{
        private boolean success;
        private String msg;
        private HistoryDetail historyDetail;
    }

    @Data
    @Builder
    public static class HistoryDetail{
        private Long id;

        private String accountNumber; // 내 계좌

        private String toAccount;

        private Long toCode;

        private String toBankName;

        private int type;

        private int transferAmount;

        private int afterBalance;

        private String sign;

        private String toSign;

        private LocalDateTime createdAt;
    }





}
