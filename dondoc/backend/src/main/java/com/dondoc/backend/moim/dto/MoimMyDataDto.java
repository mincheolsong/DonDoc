package com.dondoc.backend.moim.dto;

import lombok.Data;

import java.time.LocalDateTime;

public class MoimMyDataDto {

    @Data
    public static class TransferRequest{
        private String identificationNumber; // 모임계좌 식별번호
        private String moimAccountNumber; // 모임 계좌번호
        private String memberAccountNumber; // 멤버 계좌번호
        private String month; // 월
    }

    @Data
    public static class TransferResponse{
        private LocalDateTime transDate;
        private String name;
        private String transferAmount;
        private Integer afterBalance;
        private String content;
    }
}
