package com.dondoc.backend.user.controller;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.common.utils.ApiUtils.ApiResult;
import com.dondoc.backend.common.utils.ApiUtils;
import com.dondoc.backend.user.dto.account.AccountListDto;
import com.dondoc.backend.user.dto.account.HistoryDto;
import com.dondoc.backend.user.dto.friend.FriendRequestDto;
import com.dondoc.backend.user.entity.Account;
import com.dondoc.backend.user.service.AccountService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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

    // 계좌 저장
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

    @PostMapping("/history")
    @ApiOperation(value = "겨래내역 조회", notes = "계좌 저장 API", response = ApiUtils.class)
    public ApiResult searchHistory(@AuthenticationPrincipal UserDetails userDetails, @RequestBody HistoryDto historyDto) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            AccountListDto.Response res = accountService.searchHistory(userId, historyDto);
            return ApiUtils.success(res);
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



}
