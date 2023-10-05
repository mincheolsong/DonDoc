package com.dondoc.backend.moim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        // total, shopping, education, food, leisure, etc
        private int[] thisMonth = {0,0,0,0,0,0};
        private int[] lastMonth = {0,0,0,0,0,0};


        public void changeThisTotal(int n){
            this.thisMonth[0] += n;
        }

        public void changeThisShopping(int n){
            this.thisMonth[1]+=n;
        }

        public void changeThisEducation(int n){
            this.thisMonth[2]+=n;
        }

        public void changeThisFood(int n){
            this.thisMonth[3]+=n;
        }

        public void changeThisLeisure(int n){
            this.thisMonth[4]+=n;
        }

        public void changeThisEtc(int n){
            this.thisMonth[5]+=n;
        }



        public void changeLastTotal(int n){
            this.lastMonth[0] += n;
        }

        public void changeLastShopping(int n){
            this.lastMonth[1]+=n;
        }

        public void changeLastEducation(int n){
            this.lastMonth[2]+=n;
        }

        public void changeLastFood(int n){
            this.lastMonth[3]+=n;
        }

        public void changeLastLeisure(int n){
            this.lastMonth[4]+=n;
        }

        public void changeLastEtc(int n){
            this.lastMonth[5]+=n;
        }

    }
}
