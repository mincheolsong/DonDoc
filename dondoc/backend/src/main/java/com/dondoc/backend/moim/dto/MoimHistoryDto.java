package com.dondoc.backend.moim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class MoimHistoryDto {
    @ApiModel(value = "모임 거래내역 전체조회", description = "거래내역 전체 조회에 필요한 요청값 DTO")
    @Data
    public static class ListRequest {

        @ApiModelProperty(value = "식별번호")
        @NotBlank(message = "식별번호를 입력해주세요.")
        private String identificationNumber;

        @ApiModelProperty(value = "계좌번호")
        @NotBlank(message = "계좌번호를 입력해주세요.")
        private String accountNumber;
    }

    @ApiModel(value = "모임 거래내역 상세조회", description = "거래내역 상세조회에 필요한 요청값 DTO")
    @Data
    public static class DetailRequest {

        @ApiModelProperty(value = "식별번호")
        @NotBlank(message = "식별번호를 입력해주세요.")
        private String identificationNumber;

        @ApiModelProperty(value = "계좌번호")
        @NotBlank(message = "계좌번호를 입력해주세요.")
        private String accountNumber;

        @ApiModelProperty(value = "거래내역ID")
        @NotBlank(message = "historyId를 입력해주세요")
        private Long historyId;
    }

    @Data
    public static class Response{
        private String date; // 이체일자
        private Long historyId; // 거래내역 id
        private String toAccount; // 상대 계좌번호
        private int type; // 1 : 송금, 2 : 입금
        private int transferAmount; // 거래금액
        private int afterBalance; // 잔액
        private String content; // 출금요청의 경우 요청내용, 입금의 경우 메모

        public Response(String date, Long historyId, String toAccount, int type, int transferAmount, int afterBalance, String content) {
            this.date = date;
            this.historyId = historyId;
            this.toAccount = toAccount;
            this.type = type;
            this.transferAmount = transferAmount;
            this.afterBalance = afterBalance;
            this.content = content;
        }
    }
}
