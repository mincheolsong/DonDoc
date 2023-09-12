package com.bank.backend.dto;

import com.bank.backend.entity.Account;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import java.util.List;

public class AccountListDto {

    @ApiModel(value = "예금주 식별번호 리스트", description = "계좌 리스트를 출력하는데 사용하는 request DTO")
    @Data
    public static class Request{
        @ApiModelProperty(value = "식별번호 리스트", example = "[\"01095530160\",\"01026807453\"]")
        private List<String> identificationNumber;
    }

    @Data
    @Builder
    public static class Response{
        private Long accountId; // ID
        private String accountName; // 계좌이름
        private String accountNumber; // 계좌번호
        private int balance; // 잔액
        private Long bankCode; // 은행코드
        private String bankName; // 은행이름

        public static AccountListDto.Response toDTO(Account entity) {
            return AccountListDto.Response.builder()
                    .accountId(entity.getId())
                    .accountName(entity.getAccountName())
                    .accountNumber(entity.getAccountNumber())
                    .balance(entity.getBalance())
                    .bankCode(entity.getBankCode().getId())
                    .bankName(entity.getBankCode().getBankName())
                    .build();
        }
    }
}
