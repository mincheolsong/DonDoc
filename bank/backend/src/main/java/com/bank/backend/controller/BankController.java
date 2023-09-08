package com.bank.backend.controller;

import com.bank.backend.common.utils.ApiUtils;
import com.bank.backend.common.utils.ApiUtils.ApiResult;
import com.bank.backend.common.utils.EncryptionUtils;
import com.bank.backend.dto.AccountDetailResponseDto;
import com.bank.backend.dto.AccountListRequestDto;
import com.bank.backend.dto.AccountListResponseDto;
import com.bank.backend.entity.Account;
import com.bank.backend.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank")
public class BankController {

    private final BankService bankService;


    /**
     * 기능 : 계좌 목록조회 (송민철)
     * request body : { "identificationNumber" : ["01095530160","asdf1234"] }
     * 리턴값 : [
     *              {
     *                  Long accountId; // 계좌 ID
     *                  String accountName; // 계좌이름
     *                  String accountNumber; // 계좌번호
     *                  int balance; // 잔액
     *                  Long bankCode; // 은행코드
     *                  String bankName; // 은행이름
     *              },
     *              {
     *                  ...
     *              }
     *         ]
     **/
    @GetMapping("/list")
    public ApiResult getAccountList(@RequestBody AccountListRequestDto accountListRequestDto){
        List<String> identificationNumber = accountListRequestDto.getIdentificationNumber();
        List<AccountListResponseDto> result = new ArrayList<>();

        if(identificationNumber==null) { // 입력값을 넣지 않은 경우
            return ApiUtils.error("입력값이 없습니다", HttpStatus.BAD_REQUEST);
        }

        // result 리스트에 계좌목록 추가
        for(String number : identificationNumber){
            try{
                int cnt = bankService.findAccountList(result,number);
            }catch (Exception e){
                return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        return ApiUtils.success(result);
    }

    /**
     * 기능 : 계좌 상세조회 (송민철)
     * PathVariable : accountId (계좌 ID)
     * 리턴값 :{
     *          Long accountId; // ID
     *          String accountName; // 계좌이름
     *          String accountNumber; // 계좌번호
     *          int balance; // 잔액
     *          Long bankCode; // 은행코드
     *          String bankName; // 은행이름
     *      }
     **/
    @GetMapping("/detail/{accountId}")
    public ApiResult getAccountDetail(@PathVariable("accountId")Long accountId){
        AccountDetailResponseDto accountDetailResponseDto;

        try {
             accountDetailResponseDto = bankService.findByAccountId(accountId);
        }catch (Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ApiUtils.success(accountDetailResponseDto);
    }


}
