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
        private int thisTotal;
        private int lastTotal;
        private int thisShoppping;
        private int lastShoppping;
        private int thisEducation;
        private int lastEducation;
        private int thisFood;
        private int lastFood;
        private int thisLeisure;
        private int lastLeisure;
        private int thisEtc;
        private int lastEtc;

        public void changeThisTotal(int n){
            this.thisTotal += n;
        }

        public void changeThisShopping(int n){
            this.thisShoppping+=n;
        }

        public void changeThisEducation(int n){
            this.thisEducation+=n;
        }

        public void changeThisFood(int n){
            this.thisFood+=n;
        }

        public void changeThisLeisure(int n){
            this.thisLeisure+=n;
        }

        public void changeThisEtc(int n){
            this.thisEtc+=n;
        }



        public void changeLastTotal(int n){
            this.lastTotal += n;
        }

        public void changeLastShopping(int n){
            this.lastShoppping+=n;
        }

        public void changeLastEducation(int n){
            this.lastEducation+=n;
        }

        public void changeLastFood(int n){
            this.lastFood+=n;
        }

        public void changeLastLeisure(int n){
            this.lastLeisure+=n;
        }

        public void changeLastEtc(int n){
            this.lastEtc+=n;
        }

    }
}
