package com.bank.backend.controller;

import com.bank.backend.common.utils.ApiUtils;
import com.bank.backend.common.utils.ApiUtils.ApiResult;
import com.bank.backend.dto.*;
import com.bank.backend.entity.Memo;
import com.bank.backend.service.BankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import com.bank.backend.entity.History;
import javax.validation.Valid;


@CrossOrigin(origins = {"http://localhost:5173", "http://j9d108.p.ssafy.io:9090"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bank")
public class BankController {

    private final BankService bankService;


    /** 계좌 목록조회 **/
    @ApiOperation(value = "계좌 목록조회", notes = "예금주의 식별번호에 해당하는 계좌 목록을 보여주는 API", response = ApiResult.class)
    @PostMapping("/account/list")
    public ApiResult getAccountList(@RequestBody @ApiParam(value = "예금주 식별번호 리스트",required = true) AccountListDto.Request req){
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
    @ApiOperation(value = "계좌 상세조회", notes = "accountId에 해당하는 계좌의 상세정보를 불러오는 API", response = ApiResult.class)
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

    /** 예금주 생성 **/
    @ApiOperation(value = "예금주 생성", notes = "예금주 생성하는 API", response = ApiResult.class)
    @PostMapping("/owner/create")
    public ApiResult createOwner(@ApiParam(value = "예금주 생성에 필요한 Request Dto",required = true) @RequestBody OwnerDto.Request request) throws Exception{
        log.info("{} 예금주 생성 요청", request.getOwnerName()); // log

        // 예금주 검증
        OwnerDto.Response certification= bankService.certification(request);

        if(!certification.isPresent()){
            log.info("{} 예금주 정보가 존재합니다.", certification.getOwner().getOwnerName());
            return ApiUtils.error("예금주 정보가 존재합니다.", HttpStatus.BAD_REQUEST);
        }

        // 예금주 생성
        OwnerDto.Response response = bankService.createOwner(certification);

        log.info("{} 예금주 생성 완료", response.getOwner().getOwnerName());
        log.info("식별번호 = {}", response.getOwner().getIdentificationNumber());
        return ApiUtils.success("예금주 등록이 완료되었습니다.");
    }

    /** 계좌 개설 **/
    @ApiOperation(value = "계좌 개설", notes = "계좌 개설 API", response = ApiResult.class)
    @PostMapping("/account/create")
    public ApiResult createAccount(@ApiParam(value = "계좌 개설에 필요한 Request Dto",required = true) @RequestBody AccountDto.Request request) throws Exception {
        log.info("계좌 개설 요청");

        // 예금주 검증
        OwnerDto.Response certification = bankService.certification(request);

        // 존재하지 않는 경우 불가
        if (certification.isPresent()) {
            log.error("예금주 정보가 존재하지 않습니다.");
            return ApiUtils.error("예금주 정보가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        try{
            // 계좌 개설
            AccountDto.Response response = bankService.createAccount(certification.getOwner(), request);
            log.info("{} 계좌 생성 완료", response.getBankName() + " " + request.getAccountName() + " " + response.getAccountNumber());
            return ApiUtils.success(response);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 계좌 이체 **/
    @ApiOperation(value = "계좌 이체", notes = "계좌 이체 API", response = ApiResult.class)
    @PostMapping("/account/transfer")
    public ApiResult transfer(@ApiParam(value = "계좌 이체에 필요한 Request Dto",required = true) @RequestBody TransferDto.Request request) throws Exception{
        log.info("{} 계좌로 {}원 송금 요청", request.getToAccount(), request.getTransferAmount());

        try{
            TransferDto.Response transferDto = bankService.transfer(request);
            log.info("{}에서 {}으로 송금 완료", transferDto.getSendOwner(), transferDto.getToOwner());
            return ApiUtils.success(transferDto.getMsg());
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 계좌 실명 조회 **/
    @ApiOperation(value = "계좌 실명 조회", notes="계좌 실명 조회 API", response=ApiResult.class)
    @PostMapping("/account/certification")
    public ApiResult getAccount(@ApiParam(value = "계좌 실명 조회에 필요한 Request Dto")@RequestBody AccountCertificationDto.Request request){
        log.info("계좌 실명 조회 요청");
        try{
            AccountCertificationDto.Response certification = bankService.getAccount(request);
            log.info(certification.getOwnerName());
            return ApiUtils.success(certification);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 계좌 거래 내역 조회 */
    @ApiOperation(value = "계좌 거래 내역 전체 조회", notes = "계좌 거래내역을 모두 조회하는 API", response = ApiResult.class)
    @PostMapping("/history")
    public ApiResult<?> getHistoryList(@ApiParam(value = "거래내역 조회에 필요한 요청값",required = true) @Valid @RequestBody HistoryDto.Request req) {
        try{
            List<HistoryDto.Response> result = bankService.getHistoryList(req);
            return ApiUtils.success(result);
        } catch(Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 상세 거래 내역 조회 */
    @ApiOperation(value = "계좌 거래 상세 내역 조회", notes = "계좌 거래내역을 상세 조회하는 API", response = ApiResult.class)
    @PostMapping("/detail_history")
    public ApiResult<?> getDetailHistory(@ApiParam(value = "거래내역 조회에 필요한 요청값",required = true) @Valid @RequestBody HistoryDto.Request req) {
        try{
            HistoryDto.Response result = bankService.getDetailHistory(req);
            return ApiUtils.success(result);
        } catch(Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 거래 상세 내역 - 메모 작성 */
    @ApiOperation(value = "메모 작성", notes = "메모 작성 API", response = ApiResult.class)
    @PostMapping("/detail_history/memo")
    public ApiResult<?> writeMemo(@ApiParam(value = "메모 작성에 필요한 요청값 ",required = true) @Valid @RequestBody MemoDto.Request req) {
        try{
            MemoDto.Response result = bankService.writeMemo(req);
            return ApiUtils.success(result);
        } catch(Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 비밀번호 재설정 **/
    @ApiOperation(value = "비밀번호 재설정", notes = "비밀번호 재설정 및 활성화 API", response = ApiResult.class)
    @PostMapping("/account/password")
    public ApiResult resetPassword(@ApiParam(value = "비밀번호 재설정 Request Dto") @RequestBody PasswordDto.Request request) throws Exception{
        log.info("{} 계좌 비밀번호 재설정", request.getAccountNumber());

        try{
            PasswordDto.Response response = bankService.resetPassword(request);
            log.error(response.getMsg());
            return ApiUtils.success(response);
        }catch(Exception e){
            log.info(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
