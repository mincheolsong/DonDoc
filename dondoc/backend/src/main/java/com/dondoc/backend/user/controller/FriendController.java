package com.dondoc.backend.user.controller;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.common.utils.ApiUtils;
import com.dondoc.backend.common.utils.ApiUtils.ApiResult;
import com.dondoc.backend.user.dto.friend.FriendListDto;
import com.dondoc.backend.user.dto.friend.FriendRequestDto;
import com.dondoc.backend.user.service.FriendService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/friend")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    // 친구요청
    @ApiOperation(value = "친구요청", notes = "친구요청 API", response = ApiUtils.ApiResult.class)
    @PostMapping("/request/{friendId}")
    public ApiResult friendRequest(@PathVariable @ApiParam(value = "친구 요청 폼", required = true) Long friendId,
                @AuthenticationPrincipal UserDetails userDetails) throws Exception{
        try{
            log.info(friendId.toString());
            Long userId = Long.parseLong(userDetails.getUsername());
            FriendRequestDto.Response res = friendService.friendRequest(friendId, userId);
            return ApiUtils.success(res.getMsg());

        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 친구 요청 수락
    @ApiOperation(value = "친구요청 수락", notes = "친구요청 수락 API", response = ApiUtils.ApiResult.class)
    @PutMapping("/access/{requestId}")
    public ApiResult accessRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String requestId) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            FriendRequestDto.Response res = friendService.requestAccess(userId, requestId);
            return ApiUtils.success(res.getMsg());
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 친구 요청 거절
    @ApiOperation(value = "친구요청 거절", notes = "친구요청 거절 API", response = ApiUtils.ApiResult.class)
    @PutMapping("/deny/{requestId}")
    public ApiResult requestDeny(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String requestId) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            FriendRequestDto.Response res = friendService.requestDeny(userId,requestId);
            return ApiUtils.success(res.getMsg());
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    // 친구 요청 목록조회
    @ApiOperation(value = "친구 요청 목록조회", notes = "친구요청 거절 API", response = ApiUtils.ApiResult.class)
    @GetMapping("/request/list")
    public ApiResult requestList(@AuthenticationPrincipal UserDetails userDetails) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            FriendListDto.Response res = friendService.requestList(userId);
            return ApiUtils.success(res.getMsg());
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 친구 목록 조회
    @ApiOperation(value = "친구목록조회", notes = "친구목록조회 API", response = ApiUtils.ApiResult.class)
    @GetMapping("/list")
    public ApiResult friendList(@AuthenticationPrincipal UserDetails userDetails) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            FriendListDto.Response res = friendService.friendList(userId);
            return ApiUtils.success(res.getMsg());
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
