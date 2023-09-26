package com.dondoc.backend.moim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

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

}
