package com.bank.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class HistoryDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        @NotBlank(message = "식별번호를 입력해주세요.")
        private String identificationNumber;

        @NotBlank(message = "계좌번호를 입력해주세요.")
        private String accountNumber;

        // 상세내역 볼 때 사용
        private Long historyId;

    }

}
