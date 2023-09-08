package com.bank.backend.controller;

import com.bank.backend.common.utils.ApiUtils;
import com.bank.backend.common.utils.ApiUtils.ApiResult;
import com.bank.backend.common.utils.EncryptionUtils;
import com.bank.backend.service.BankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bank.backend.dto.HistoryDto;
import com.bank.backend.entity.History;

import javax.validation.Valid;
import java.util.List;

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
