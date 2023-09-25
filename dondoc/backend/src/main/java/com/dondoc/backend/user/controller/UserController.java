package com.dondoc.backend.user.controller;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.common.utils.ApiUtils;
import com.dondoc.backend.common.utils.ApiUtils.ApiResult;
import com.dondoc.backend.user.dto.user.*;
import com.dondoc.backend.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@Api(value = "User", description = "User Controller", tags = "유저 API")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @ApiOperation(value = "회원가입", notes = "회원가입을 진행하는 API", response = ApiResult.class)
    @PostMapping("/signup")
    public ApiResult signUp(@RequestBody @ApiParam(value = "회원가입 정보", required = true) SignUpDto.Request req) throws Exception{
        SignUpDto.Response res = userService.signUp(req);
        if(!res.isSuccess()){
            return ApiUtils.error(res.getMsg(),HttpStatus.BAD_REQUEST);
        }
        return ApiUtils.success(res.getMsg());
    }

    // 문자인증
    @PostMapping("/sms/signup")
    @ApiOperation(value = "문자인증(시작안함)", notes = "인증번호 발송 API", response = ApiResult.class)
    public ApiResult sendSignUpSMS(@RequestBody @ApiParam(value = "인증번호 전송", required = true)String phoneNumber){

        CertificationDto.Response res = userService.sendSMS(phoneNumber);

        return ApiUtils.success(res);
    }

    @PostMapping("/sms/find_password")
    @ApiOperation(value = "문자인증(시작안함)", notes = "인증번호 발송 API", response = ApiResult.class)
    public ApiResult sendFindPasswordSMS(@RequestBody @ApiParam(value = "인증번호 전송", required = true)String phoneNumber){
        // 유저 존재 여부 확인
        try {
            CertificationDto.Response res = userService.sendSMS(phoneNumber);
            return ApiUtils.success(res);
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // 로그인(Token 자동 Header 및 Cookie 등록)
    @PostMapping("/signin")
    @ApiOperation(value = "로그인", notes = "로그인 API", response = ApiResult.class)
    public ApiResult signIn(@RequestBody @ApiParam(value = "아이디 & 비밀번호")
                            SignInDto.Request req, HttpServletResponse response) throws Exception {
        try{
            SignInDto.Response res = userService.signIn(req);

            // Header에 등록
            response.setHeader("Authorization", res.getAccessToken());

            // Cookie에 저장
            response.addCookie(res.getRefreshToken());

            return ApiUtils.success(res);
        }catch(Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    // 로그아웃
    @GetMapping("/logout")
    @ApiOperation(value = "로그아웃(시작안함)", notes = "로그아웃 API", response = ApiResult.class)
    public ApiResult logout(HttpServletResponse response){
        Cookie cookie = new Cookie("refreshToken","");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ApiUtils.success("로그아웃이 완료되었습니다.");
    }

    // 비밀번호 찾기 || 휴대전화 인증은 별도로
    @PutMapping("/find_password")
    @ApiOperation(value = "비밀번호 찾기 -> 비밀번호 변경", notes = "비밀번호 찾기 API", response = ApiResult.class)
    public ApiResult findPassword(@RequestBody FindPasswordDto.Request req){
        //비밀번호 변경
        try{
            FindPasswordDto.Response res = userService.findPassword(req);
            return ApiUtils.success(res.getMsg());
        }catch(NoSuchElementException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 회원 정보 변경
    @PutMapping("/update")
    @ApiOperation(value = "회원정보 변경", notes = "회원정보 변경 API", response = ApiResult.class)
    public ApiResult updateUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateUserDto.Request req){
        try{
            UpdateUserDto.Response res = userService.updateUser(req, Long.parseLong(userDetails.getUsername()));
            return ApiUtils.success(res.getMsg());
        }catch(Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    // 프로필 검색
    @GetMapping("/profile/{userId}")
    @ApiOperation(value = "프로필 검색", notes = "프로필 검색 API", response = ApiResult.class)
    public ApiResult findProfile(@PathVariable @ApiParam(value = "유저 아이디") Long userId ,@AuthenticationPrincipal UserDetails userDetails){
        try{
            log.info(userDetails.getUsername());
            log.info(userId.toString());
            if(Long.parseLong(userDetails.getUsername()) == userId){
                ProfileDto.Response profileDto = userService.myProfile(userId);
                return ApiUtils.success(profileDto);
            }else{
                ProfileDto.Response profileDto = userService.findProfile(userId);
                return ApiUtils.success(profileDto);
            }
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    // 사용자 검색(친구 추가 또는 모임에서 멤버 초대할때) => 핸드폰 번호로 검색 => don't search me
    @GetMapping("/find_user/{phoneNumber}")
    @ApiOperation(value = "사용자 검색", notes = "사용자 검색 API", response = ApiResult.class)
    public ApiResult findUser(@PathVariable @ApiParam(value = "핸드폰번호") String phoneNumber, @AuthenticationPrincipal UserDetails userDetails){
        try{
            FindUserDto.Response findUserDto = userService.findUser(phoneNumber, userDetails.getUsername());
            return ApiUtils.success(findUserDto);
        }catch (Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/test")
    @ApiOperation(value = "UserDetails Test",notes="test",response=ApiResult.class)
    public ApiResult test(@AuthenticationPrincipal UserDetails userDetails){
        if(userDetails != null){
            log.info(userDetails.getUsername());
            log.info(userDetails.getAuthorities().toString());
            return ApiUtils.success("good");
        }
            return ApiUtils.error("bad", HttpStatus.UNAUTHORIZED);
    }


}
