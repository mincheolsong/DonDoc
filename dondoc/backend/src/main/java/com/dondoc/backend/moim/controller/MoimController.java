package com.dondoc.backend.moim.controller;

import com.dondoc.backend.common.utils.ApiUtils;
import com.dondoc.backend.common.utils.ApiUtils.ApiResult;
import com.dondoc.backend.common.utils.EncryptionUtils;
import com.dondoc.backend.moim.dto.*;
import com.dondoc.backend.moim.entity.Mission;
import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.service.MoimService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://j9d108.p.ssafy.io:9090"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moim")
public class MoimController {

    private final MoimService moimService;

    @PostMapping("/create")
    public ApiResult createMoim(@RequestBody MoimCreateDto.Request req){
        // 식별번호 생성
        String identificationNumber = EncryptionUtils.makeIdentificationNumber();

        // request body 값
        String moimName = req.getMoimName();
        String password = req.getPassword();
        String introduce = req.getIntroduce();
        int moimType = req.getMoimType();

        // 생성된 계좌번호
        String moimAccount = null;

        // API : /bank/owner/create (예금주 생성)
        // param : 식별번호, 모임이름
        if(!moimService.createOnwerAPI(identificationNumber,req.getMoimName())){
            return ApiUtils.error("예금주 생성에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }

        // API : /bank/account/create (계좌 개설)
        // param : 모임이름, bankCode(108), 식별번호, 비밀번호
        moimAccount = moimService.createAccountAPI(moimName,108,identificationNumber,password);
        if(moimAccount==null){
            return ApiUtils.error("모임 생성에 실패했습니다.",HttpStatus.BAD_REQUEST);
        }

        try {
            // 1. 현재 로그인한 User 엔티티 찾기 (refreshToken의 헤더값에서 userId가져오기)

            // 2. Moim 엔티티 생성
            Moim moim = moimService.createMoim(identificationNumber, moimName, introduce, moimAccount, 0, moimType);
            // 3. MoimMember 엔티티 생성 (

            // 4.
            return ApiUtils.success(MoimCreateDto.Response.toDTO(moim));
        }catch (Exception e){
            return ApiUtils.error("모임 생성 실패",HttpStatus.BAD_REQUEST);
        }

    }


    /** 관리자에게 출금 요청 */
    @ApiOperation(value = "출금 요청", notes = "관리자에게 출금 요청하는 API", response = ApiResult.class)
    @PostMapping("/withdraw_req")
    public ApiResult<?> withdrawReq(@ApiParam(value = "출금 요청에 필요한 Request Dto",required = true) @Valid @RequestBody WithdrawRequestDto.Request req) {
        try{
            WithdrawRequestDto.Response result = moimService.withdrawReq(req);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 관리자에게 미션 요청 */
    @ApiOperation(value = "미션 요청", notes = "관리자에게 미션 요청하는 API", response = ApiResult.class)
    @PostMapping("/mission_req")
    public ApiResult<?> missionReq(@ApiParam(value = "미션 요청에 필요한 Request Dto",required = true) @Valid @RequestBody MissionRequestDto.Request req) {
        try{
            MissionRequestDto.Response result = moimService.missionReq(req);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 요청 관리/목록 - 전체 리스트 조회 */
    @ApiOperation(value = "요청 리스트 조회", notes = "출금/미션의 요청 리스트를 조회하는 API", response = ApiResult.class)
    @PostMapping("/list_req")
    public ApiResult<?> getRequestList(@ApiParam(value = "요청 리스트 조회에 필요한 Request Dto",required = true) @Valid @RequestBody AllRequestDto.Request req) {
        try{
            AllRequestDto.Response result = moimService.getRequestList(req);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 요청 상세조회 */
    @ApiOperation(value = "요청 상세조회", notes = "출금/미션의 요청을 상세 조회하는 API", response = ApiResult.class)
    @PostMapping("/detail_req")
    public ApiResult<?> getRequestDetail(@ApiParam(value = "요청 상세조회에 필요한 Request Dto",required = true) @Valid @RequestBody DetailRequestDto.Request req) {
        try{
            DetailRequestDto.Response result = moimService.getRequestDetail(req);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 출금 요청 승인 */
    @ApiOperation(value = "출금요청 승인", notes = "관리자가 출금 요청을 승인하는 API", response = ApiResult.class)
    @PostMapping("/allow_req")
    public ApiResult<?> allowRequest(@ApiParam(value = "출금요청 승인에 필요한 Request Dto",required = true) @Valid @RequestBody AllowRequestDto.Request req) {
        try{
            AllowRequestDto.Response result = moimService.allowRequest(req);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /** 출금 요청 거절 */
    @ApiOperation(value = "출금요청 거절", notes = "관리자가 출금 요청을 거절하는 API", response = ApiResult.class)
    @PostMapping("/reject_req")
    public ApiResult<?> rejectRequest(@ApiParam(value = "출금요청 거절에 필요한 Request Dto",required = true) @Valid @RequestBody RejectRequestDto.Request req) {
        try{
            String result = moimService.rejectRequest(req);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 미션 요청 승인 */
    @ApiOperation(value = "미션요청 승인", notes = "관리자가 미션 요청을 승인하는 API", response = ApiResult.class)
    @PostMapping("/allow_mission")
    public ApiResult<?> allowMissionRequest(@ApiParam(value = "미션요청 승인에 필요한 Request Dto",required = true) @Valid @RequestBody AllowRequestDto.Request req) {
        try{
            AllowRequestDto.Response result = moimService.allowMissionRequest(req);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /** 미션 요청 거절 */
    @ApiOperation(value = "미션요청 거절", notes = "관리자가 미션 요청을 거절하는 API", response = ApiResult.class)
    @PostMapping("/reject_mission")
    public ApiResult<?> rejectMissionRequest(@ApiParam(value = "미션요청 거절에 필요한 Request Dto",required = true) @Valid @RequestBody RejectRequestDto.Request req) {
        try{
            String result = moimService.rejectMissionRequest(req);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 미션 성공 */
    @ApiOperation(value = "미션 성공", notes = "관리자가 미션성공 인증하는 API", response = ApiResult.class)
    @PostMapping("/success_mission")
    public ApiResult<?> successMission(@ApiParam(value = "미션 성공에 필요한 Request Dto",required = true) @Valid @RequestBody SuccessOrNotMissionDto.Request req) {
        try{
            SuccessOrNotMissionDto.Response result = moimService.successMission(req);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /** 나의 미션 조회 */
    @ApiOperation(value = "나의 미션 조회", notes = "내 미션 리스트를 조회하는 API", response = ApiResult.class)
    @GetMapping("/my_mission/{userId}")
    public ApiResult<?> getMyMission(@ApiParam(value = "미션 리스트를 조회에 필요한 회원 ID",required = true) @Valid @PathVariable Long userId) {
        try{
            List<MissionInfoDto.Response> result = moimService.getMyMission(userId);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
