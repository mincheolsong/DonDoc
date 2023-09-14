package com.dondoc.backend.moim.controller;

import com.dondoc.backend.common.utils.ApiUtils;
import com.dondoc.backend.common.utils.ApiUtils.ApiResult;
import com.dondoc.backend.common.utils.EncryptionUtils;
import com.dondoc.backend.moim.dto.MoimCreateDto;
import com.dondoc.backend.moim.service.MoimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:5173", "http://j9d108.p.ssafy.io:9090"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moim")
public class MoimController {

    private final MoimService moimService;
    @PostMapping("/create")
    public ApiResult createMoim(@RequestBody MoimCreateDto.Request req){
        // 식별번호 만들기
        String identificationNumber = EncryptionUtils.makeIdentificationNumber();

        // 식별번호와 모임이름으로 예금주 생성 (API : /bank/owner/create)
        if(!moimService.createOnwerAPI(identificationNumber,req.getMoimName())){
            return ApiUtils.error("예금주 생성에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }

        // 모임이름, bankCode(108), 식별번호, 비밀번호로 계좌 개설 (API : /bank/account/create)
        if(!moimService.createAccountAPI(req.getMoimName(),108,identificationNumber,req.getPassword())){
            return ApiUtils.error("모임계좌 생성에 실패했습니다",HttpStatus.BAD_REQUEST);
        }



        return ApiUtils.success("test");
    }
}
