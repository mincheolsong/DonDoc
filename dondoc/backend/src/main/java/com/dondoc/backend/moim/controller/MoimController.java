package com.dondoc.backend.moim.controller;

import com.dondoc.backend.common.utils.ApiUtils;
import com.dondoc.backend.common.utils.ApiUtils.ApiResult;
import com.dondoc.backend.common.utils.EncryptionUtils;
import com.dondoc.backend.moim.dto.*;
import com.dondoc.backend.moim.entity.Mission;
import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.moim.entity.MoimMember;
import com.dondoc.backend.moim.entity.WithdrawRequest;
import com.dondoc.backend.moim.service.MoimMemberService;
import com.dondoc.backend.moim.service.MoimService;
import com.dondoc.backend.user.entity.Account;
import com.dondoc.backend.user.entity.User;
import com.dondoc.backend.user.service.AccountService;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import javax.validation.Valid;


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
            User user = userService.findById(Long.parseLong(userDetails.getUsername()));
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
    public ApiResult inviteCheck(@RequestBody MoimInviteCheck.Request req) {
        Long userId = req.getUserId();
        Long moimId = req.getMoimId();
        Boolean accept = req.getAccept();
        try {
            MoimMember moimMember = moimMemberService.findMoimMember(userId.toString(), moimId);
            if (accept) { // 요청 수락
                moimMemberService.acceptMoimMember(moimMember.getId());
                return ApiUtils.success("요청이 수락되었습니다.");
            } else { // 요청 거절
                moimMemberService.deleteMoimMember(moimMember);
                return ApiUtils.success("요청이 수락되었습니다.");
            }
        } catch (Exception e) {
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
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

    /** 미션 실패 */
    @ApiOperation(value = "미션 실패", notes = "관리자가 미션실패 인증하는 API", response = ApiResult.class)
    @PostMapping("/fail_mission")
    public ApiResult<?> failMission(@ApiParam(value = "미션 실패에 필요한 Request Dto",required = true) @Valid @RequestBody SuccessOrNotMissionDto.Request req) {
        try{
            SuccessOrNotMissionDto.Response result = moimService.failMission(req);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /** 미션 포기 */
    @ApiOperation(value = "미션 포기", notes = "회원이 미션 포기하는 API", response = ApiResult.class)
    @PostMapping("/quit_mission")
    public ApiResult<?> quitMission(@ApiParam(value = "미션 포기에 필요한 Request Dto",required = true) @Valid @RequestBody SuccessOrNotMissionDto.Request req) {
        try{
            SuccessOrNotMissionDto.Response result = moimService.quitMission(req);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /** 나의 미션 조회 */
    @ApiOperation(value = "나의 미션 조회", notes = "내 미션 리스트를 조회하는 API", response = ApiResult.class)
    @GetMapping("/my_mission")
    public ApiResult<?> getMyMission(@AuthenticationPrincipal UserDetails userDetails) {
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            List<MissionInfoDto.Response> result = moimService.getMyMission(userId);
            return ApiUtils.success(result);
        }catch(Exception e){
            log.error(e.getMessage());
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
