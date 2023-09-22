package com.dondoc.backend.moim.controller;

import com.dondoc.backend.common.utils.ApiUtils;
import com.dondoc.backend.common.utils.ApiUtils.ApiResult;
import com.dondoc.backend.common.utils.EncryptionUtils;
import com.dondoc.backend.moim.dto.*;
import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.entity.MoimMember;
import com.dondoc.backend.moim.entity.WithdrawRequest;
import com.dondoc.backend.moim.service.MoimMemberService;
import com.dondoc.backend.moim.service.MoimService;
import com.dondoc.backend.user.entity.Account;
import com.dondoc.backend.user.entity.User;
import com.dondoc.backend.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
@Api(value = "Moim", description = "모임 컨트롤러", tags = "모임 API")
@CrossOrigin(origins = {"http://localhost:5173", "http://j9d108.p.ssafy.io:9090"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moim")
public class MoimController {

    private final MoimService moimService;
    private final MoimMemberService moimMemberService;
    private final UserService userService;
    private final AccountService accountService;
    @ApiOperation(value = "모임 생성", notes = "모임을 생성하는 API", response = ApiResult.class)
    @PostMapping("/create")
    public ApiResult createMoim(@ApiParam(value = "모임 생성에 필요한 값",required = true) @RequestBody MoimCreateDto.Request req, Authentication authentication){

        /**
         * 식별번호 생성
         **/
        String identificationNumber;
        try {
            identificationNumber = moimService.makeIdentificationNumber();
        }catch (Exception e){ // 중복된 식별번호가 생성되면 exception 발생
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        // request body로 넘어온 값
        String moimName = req.getMoimName();
        String password = req.getPassword();
        String introduce = req.getIntroduce();
        int moimType = req.getMoimType();
        Long accountId = req.getAccountId();
        List<MoimCreateDto.InviteDto> manager = req.getManager();


        /**
         * 예금주 생성
         * API : /bank/owner/create
         * param : 식별번호, 모임이름
         **/
        if(!moimService.createOnwerAPI(identificationNumber,req.getMoimName())){
            return ApiUtils.error("예금주 생성에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }

        /**
         * 계좌 개설
         * API : /bank/account/create
         * param : 모임이름, bankCode(108), 식별번호, 비밀번호
         **/
        Map<String,Object> createResult = moimService.createAccountAPI(moimName,108,identificationNumber,password);
        // 모임 계좌번호
        String moimAccountNumber = createResult.get("accountNumber").toString();
        // 모임 계좌 ID
        Long moimAccountId = (Long)createResult.get("accountId");

        if(moimAccountNumber==null){
            return ApiUtils.error("모임 생성에 실패했습니다.",HttpStatus.BAD_REQUEST);
        }


        try {
            // 1. 현재 로그인한 User 엔티티 찾기 (token 헤더값에서 userId가져오기)
            UserDetails userDetails = (UserDetails)authentication.getPrincipal();
            User user = userService.findById(userDetails.getUsername());
            // 2. Moim 엔티티 생성
            Moim moim = moimService.createMoim(identificationNumber, moimName, introduce, moimAccountId,moimAccountNumber, 0, moimType, manager.size());
            // 3. Account 엔티티 찾기 (reqDTO로 받은 accountId를 활용해서)
            Account account = accountService.findById(accountId);
            // 4. MoimMember 엔티티 생성 (User 엔티티, Moim 엔티티, Account 엔티티 활용)
            int cnt = moimMemberService.createMoimMember(user,moim,LocalDateTime.now(),account, manager);
            return ApiUtils.success(MoimCreateDto.Response.toDTO(moim));
        }catch (Exception e){
            return ApiUtils.error(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/list") // 헤더의 토큰을 이용해서 가져온 userId를 이용해서 moimList를 조회
    public ApiResult getMoimList(Authentication authentication){
        // 현재 로그인한 User 엔티티 찾기 (token 헤더값에서 userId가져오기)
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        List<MoimListDto.Response> result = new ArrayList<>();

        try {
            String userId = userDetails.getUsername();
            List<Moim> moimList = moimService.getMoimList(userId);
            for(Moim m : moimList){ // 엔티티를 dto로 변환
                result.add(MoimListDto.Response.toDTO(m));
            }
            return ApiUtils.success(result);
        }catch (Exception e){
            return ApiUtils.error(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/detail/{moimId}")
    public ApiResult getMoimDetail(@PathVariable("moimId")Long moimId,Authentication authentication){

        try{
            // 현재 로그인한 User 엔티티 찾기 (token 헤더값에서 userId가져오기)
            UserDetails userDetails = (UserDetails)authentication.getPrincipal();
            String userId = userDetails.getUsername();

            MoimDetailDto.Response result = moimService.getMoimDetail(userId,moimId);

            return ApiUtils.success(result);

        }catch (Exception e){
            return ApiUtils.error(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/invite")
    public ApiResult invite(@RequestBody MoimInviteDto.Request req){
        Long moimId = req.getMoimId();
        List<MoimInviteDto.InviteDto> inviteList = req.getInvite();
        int cnt;
        try {
            Moim moim = moimService.findById(moimId);
            cnt = moimMemberService.inviteMoimMember(moim,inviteList);

        }catch (Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ApiUtils.success( "moimId = " + moimId + "에 " + cnt + "명 초대 성공");
    }

    @PostMapping("/invite/check")
    public ApiResult inviteCheck(@RequestBody MoimInviteCheck.Request req){
        Long userId = req.getUserId();
        Long moimId = req.getMoimId();
        Boolean accept = req.getAccept();
        try {
            MoimMember moimMember = moimMemberService.findMoimMember(userId.toString(), moimId);
            if(accept){ // 요청 수락
                moimMemberService.acceptMoimMember(moimMember.getId());
            }else{ // 요청 거절
                moimMemberService.deleteMoimMember(moimMember);
            }
        }catch (Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
