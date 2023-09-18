package com.bank.backend.dto;

import com.bank.backend.entity.BankCode;
import com.bank.backend.entity.History;
import com.bank.backend.entity.Memo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
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

    @Builder
    @Data
    public static class Response {

        private History historyId;
        private BankCodeDto toCode;
        private String memo;

        public static HistoryDto.Response toDTO(History entity) {
            return HistoryDto.Response.builder()
                    .historyId(entity)
                    .toCode(BankCodeDto.fromEntity(entity.getToCode()))
                    .build();
        }

        public static HistoryDto.Response toDTO(History entity, Memo memo) {
            return HistoryDto.Response.builder()
                    .historyId(entity)
                    .toCode(BankCodeDto.fromEntity(entity.getToCode()))
                    .memo(memo.getContent())
                    .build();
        }
    }

}
