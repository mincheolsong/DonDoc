package com.bank.backend.controller;

import com.bank.backend.common.utils.ApiUtils;
import com.bank.backend.common.utils.ApiUtils.ApiResult;
import com.bank.backend.common.utils.EncryptionUtils;
import com.bank.backend.dto.AccountDetailResponseDto;
import com.bank.backend.dto.AccountListRequestDto;
import com.bank.backend.dto.AccountListResponseDto;
import com.bank.backend.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import com.bank.backend.dto.HistoryDto;
import com.bank.backend.entity.History;
import javax.validation.Valid;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bank")
public class BankController {

    private final BankService bankService;
    // 식별번호 암호화 키
    @Value("${owner.salt}")
    private String salt;

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
    @PostMapping("/account/list")
    public ApiResult getAccountList(@RequestBody AccountListRequestDto accountListRequestDto){
        List<String> identificationNumber = accountListRequestDto.getIdentificationNumber();
        List<AccountListResponseDto> result = new ArrayList<>();

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
    @GetMapping("/account/detail/{accountId}")
    public ApiResult getAccountDetail(@PathVariable("accountId")Long accountId){
        AccountDetailResponseDto accountDetailResponseDto;

        try {
             accountDetailResponseDto = bankService.findByAccountId(accountId);
        }catch (Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ApiUtils.success(accountDetailResponseDto);
    }


    // 예금주 생성
    @PostMapping("/owner/create")
    public ApiResult createOwner(@RequestBody Map<String, String> info) throws Exception{
        log.info("create owner - {}", info.toString());

        // salt 의 위치에 따라 구현 변견 가능 => 영서, 민철의 코드 참고
        // 해싱
        String identification = EncryptionUtils.encryption(info.get("identificationNumber"), salt);

        // 예금주 검증
        boolean certification= bankService.certification(identification);

        if(!certification){
            return ApiUtils.error("예금주 정보가 존재합니다.", HttpStatus.BAD_REQUEST);
        }

        // 해싱 적용
        info.put("identificationNumber", identification);

        // 예금주 생성
        bankService.createOwner(info);

        return ApiUtils.success("예금주 등록이 완료되었습니다.");
    }

    // 계좌 개설
    @PostMapping("/account/create")
    public ApiResult createAccount(@RequestBody Map<String, String> info) throws Exception{
        String identification = EncryptionUtils.encryption(info.get("identificationNumber"), salt);

        // 예금주 검증
        boolean certification = bankService.certification(identification);

        // 존재하지 않는 경우 불가
        if(certification){
            return ApiUtils.error("예금주 정보가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        // 계좌 개수 확인 메서드 필요 - 5개 제한
        boolean creatable = bankService.countAccount(identification);

        // 개수 제한 여부 확인
        if(!creatable){
            return ApiUtils.error("생성가능한 계좌의 수가 초과했습니다.", HttpStatus.BAD_REQUEST);
        }

        // 존재하므로 계좌 개설 수행
        info.put("identificationNumber", identification);

        // 계좌에 대한 salt 생성
        String tempSalt = EncryptionUtils.makeSalt();

        // 비밀번호 암호화
        info.put("password", EncryptionUtils.encryption(info.get("password"), tempSalt));

        // 개별 암호화 키 추가
        info.put("salt", tempSalt);

        // 계좌 개설
        bankService.createAccount(info);

        return ApiUtils.success("계좌 생성이 완료되었습니다.");
    }

    @PostMapping("/account/transfer")
    public ApiResult transfer(@RequestBody Map<String, String> info){


        return ApiUtils.success("계좌 이체가 완료되었습니다.");
    }
    
    /** 계좌 거래 내역 조회 */
    @PostMapping("/history")
    public ApiResult<?> getHistoryList(@Valid @RequestBody HistoryDto.Request req) {
        try{
            List<History> result = bankService.getHistoryList(req);
            return ApiUtils.success(result);
        } catch(Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /** 상세 거래 내역 조회 */
    @PostMapping("/detail_history")
    public ApiResult<?> getDetailHistory(@Valid @RequestBody HistoryDto.Request req) {
        try{
            History result = bankService.getDetailHistory(req);
            return ApiUtils.success(result);
        } catch(Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
