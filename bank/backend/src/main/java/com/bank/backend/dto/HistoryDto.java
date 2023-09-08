package com.bank.backend.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

public class HistoryDto {

    @ApiModel(value = "계좌 거래내역 조회", description = "거래내역 조회에 필요한 요청값 DTO")
    @Data
    public static class Request {

        @ApiModelProperty(value = "식별번호") // example 속성으로 초기값을 설정할 수 있음
        @NotBlank(message = "식별번호를 입력해주세요.")
        private String identificationNumber;

        @ApiModelProperty(value = "계좌번호")
        @NotBlank(message = "계좌번호를 입력해주세요.")
        private String accountNumber;

        // 상세내역 볼 때 사용
        @ApiModelProperty(value = "거래내역ID")
        private Long historyId;

    }

}
