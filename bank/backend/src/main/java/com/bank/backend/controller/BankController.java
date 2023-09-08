package com.bank.backend.controller;

import com.bank.backend.common.utils.ApiUtils;
import com.bank.backend.common.utils.ApiUtils.ApiResult;
import com.bank.backend.common.utils.EncryptionUtils;
import com.bank.backend.dto.*;
import com.bank.backend.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.bank.backend.entity.History;
import javax.validation.Valid;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bank")
public class BankController {

    private final BankService bankService;


    /** 계좌 목록조회 **/
    @PostMapping("/account/list")
    public ApiResult getAccountList(@RequestBody AccountListDto.Request req){
        List<String> identificationNumber = req.getIdentificationNumber();
        List<AccountListDto.Response> result = new ArrayList<>();

        if(identificationNumber==null || identificationNumber.size()==0) { // 입력값을 넣지 않은 경우
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


    /** 계좌 상세조회 **/
    @GetMapping("/account/detail/{accountId}")
    public ApiResult getAccountDetail(@PathVariable("accountId")Long accountId){
        AccountDetailDto.Response result;

        try {
            result = bankService.findByAccountId(accountId);
        }catch (Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ApiUtils.success(result);
    }

    @PostMapping("/owner/create")
    public ApiResult createOwner(@RequestBody OwnerDto.Request request) throws Exception{
        // 예금주 검증
        OwnerCertificationDto.Response certification= bankService.certification(request);

        if(!certification.isPresent()){
            return ApiUtils.error("예금주 정보가 존재합니다.", HttpStatus.BAD_REQUEST);
        }

        // 예금주 생성
        bankService.createOwner(certification);

        return ApiUtils.success("예금주 등록이 완료되었습니다.");
    }

    // 계좌 개설
    @PostMapping("/account/create")
    public ApiResult createAccount(@RequestBody AccountDto.Request request) throws Exception {
        // 예금주 검증
        OwnerCertificationDto.Response certification = bankService.certification(request);

        // 존재하지 않는 경우 불가
        if (certification.isPresent()) {
            return ApiUtils.error("예금주 정보가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);

        }

        // 계좌 개설
        AccountDto.Response response = bankService.createAccount(certification.getOwner(), request);

        if (!response.isSuccess()) {
            return ApiUtils.error(response.getMsg(), HttpStatus.BAD_REQUEST);
        }

        return ApiUtils.success(response.getMsg());
    }

    // 계좌 이체
    @PostMapping("/account/transfer")
    public ApiResult transfer(@RequestBody TransferDto.Request request) throws Exception{
        TransferDto.Response transferDto = bankService.transfer(request);

        if(!transferDto.isSuccess()){
            return ApiUtils.error(transferDto.getMsg(), HttpStatus.BAD_REQUEST);
        }
        return ApiUtils.success(transferDto.getMsg());
    }

    // 계좌 실명 조회
    @PostMapping("/account/certification")
    public ApiResult getAccount(@RequestBody AccountCertificationDto.Request request){
        AccountCertificationDto.Response certification = bankService.getAccount(request);
        if(!certification.isSuccess()){
            return ApiUtils.error(certification.getMsg(), HttpStatus.BAD_REQUEST);
        }
        return ApiUtils.success(certification);
    }

    /** 계좌 거래 내역 조회 */
    @PostMapping("/history")
    public ApiResult<?> getHistoryList(@Valid @RequestBody HistoryDto.Request req) {
        try{
            List<History> result = bankService.getHistoryList(req);
            return ApiUtils.success(result);
        } catch(Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 상세 거래 내역 조회 */
    @PostMapping("/detail_history")
    public ApiResult<?> getDetailHistory(@Valid @RequestBody HistoryDto.Request req) {
        try{
            History result = bankService.getDetailHistory(req);
            return ApiUtils.success(result);
        } catch(Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
