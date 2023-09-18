package com.dondoc.backend.moim.controller;

import com.dondoc.backend.common.utils.ApiUtils;
import com.dondoc.backend.common.utils.ApiUtils.ApiResult;
import com.dondoc.backend.common.utils.EncryptionUtils;
import com.dondoc.backend.moim.dto.AllRequestDto;
import com.dondoc.backend.moim.dto.MissionRequestDto;
import com.dondoc.backend.moim.dto.MoimCreateDto;
import com.dondoc.backend.moim.dto.WithdrawRequestDto;
import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.service.MoimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @PostMapping("/withdraw_req")
    public ApiResult<?> withdrawReq(@Valid @RequestBody WithdrawRequestDto.Request req) {
        try{
            WithdrawRequestDto.Response result = moimService.withdrawReq(req);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 관리자에게 미션 요청 */
    @PostMapping("/mission_req")
    public ApiResult<?> missionReq(@Valid @RequestBody MissionRequestDto.Request req) {
        try{
            MissionRequestDto.Response result = moimService.missionReq(req);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 요청 관리/목록 - 전체 리스트 조회 */
    @PostMapping("/list_req")
    public ApiResult<?> getRequestList(@Valid @RequestBody AllRequestDto.Request req) {
        try{
            AllRequestDto.Response result = moimService.getRequestList(req);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
