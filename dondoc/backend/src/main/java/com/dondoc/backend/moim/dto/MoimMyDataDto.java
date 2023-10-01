package com.dondoc.backend.moim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

public class MoimMyDataDto {
    @ApiModel(value="마이데이터 이체내역", description="마이데이터 이체내역 조회에 필요한 Request Dto")
    @Data
    public static class TransferRequest{
        @ApiModelProperty(value = "모임계좌 식별번호", example = "30094150669")
        private String identificationNumber; // 모임계좌 식별번호
        @ApiModelProperty(value = "모임 계좌번호", example = "9165326027442")
        private String moimAccountNumber; // 모임 계좌번호
        @ApiModelProperty(value = "멤버 계좌번호", example = "4563035162653")
        private String memberAccountNumber; // 멤버 계좌번호
        @ApiModelProperty(value = "조회할 달", example = "9")
        private String month; // 월
    }

    @Data
    public static class TransferResponse{
        private String date;
        private String name;
        private String transferAmount;
        private Integer afterBalance;
        private String content;

        public TransferResponse(String date, String name, String transferAmount, Integer afterBalance, String content) {
            this.date = date;
            this.name = name;
            this.transferAmount = transferAmount;
            this.afterBalance = afterBalance;
            this.content = content;
        }
    }

    @Data
    public static class SpendingAmountResponse{
        private int total;
        private int shoppping;
        private int education;
        private int food;
        private int leisure;
        private int etc;

        public void changeTotal(int n){
            this.total += n;
        }

        public void changeShopping(int n){
            this.shoppping+=n;
        }

        public void changeEducation(int n){
            this.education+=n;
        }

        public void changeFood(int n){
            this.food+=n;
        }

        public void changeLeisure(int n){
            this.leisure+=n;
        }

        public void changeEtc(int n){
            this.etc+=n;
        }
    }
}
