package com.dondoc.backend.user.controller;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.common.utils.ApiUtils;
import com.dondoc.backend.common.utils.ApiUtils.ApiResult;
import com.dondoc.backend.user.dto.account.AccountDto;
import com.dondoc.backend.user.dto.account.AccountListDto;
import com.dondoc.backend.user.dto.account.HistoryDto;
import com.dondoc.backend.user.service.AccountService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // 계좌 목록 불러오기(은행 API)
    @GetMapping("/list/bank")
    @ApiOperation(value = "계좌 목록 불러오기", notes = "계좌 불러오기 API", response = ApiUtils.class)
    public ApiResult loadAccount(@AuthenticationPrincipal UserDetails userDetails) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            AccountListDto.Response res = accountService.loadBankList(userId);
            return ApiUtils.success(res);
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 등록 계좌 목록 불러오기(서비스)
    @GetMapping("/list")
    @ApiOperation(value = "등록 계좌 목록 불러오기", notes = "등록 계좌 불러오기 API", response = ApiUtils.class)
    public ApiResult selectAccount(@AuthenticationPrincipal UserDetails userDetails) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            AccountListDto.Response res = accountService.loadList(userId);
            return ApiUtils.success(res);
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 사용 계좌 선택
    @PostMapping("/list/save")
    @ApiOperation(value = "계좌 저장", notes = "계좌 저장 API", response = ApiUtils.class)
    public ApiResult selectAccount(@AuthenticationPrincipal UserDetails userDetails, @RequestBody List<AccountListDto.Request> accountList) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            AccountListDto.Response res = accountService.saveList(userId, accountList);
            return ApiUtils.success(res);
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 거래 내역 조회
    @GetMapping("/history/{accountNumber}")
    @ApiOperation(value = "겨래내역 조회", notes = "계좌 저장 API", response = ApiUtils.class)
    public ApiResult searchHistory(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String accountNumber) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            HistoryDto.Response res = accountService.searchHistory(userId, accountNumber);
            return ApiUtils.success(res);
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 대표계좌 설정
    @GetMapping("/set")
    @ApiOperation(value = "대표계좌 설정", notes = "대표 계좌 설정 API", response = ApiUtils.class)
    public ApiResult setAccount(@AuthenticationPrincipal UserDetails userDetails, Long accountId) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            AccountDto.Response res = accountService.setAccount(userId, accountId);
            return ApiUtils.success(res);
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    // 계좌 이체 요청 // 계좌 실명조회

    // 계좌 상세 조회

    // 거래 상세 내역 확인

    // 거래 상세 내역 메모 작성

    // 비밀번호 재설정? 이거는 추후에 추가


}
