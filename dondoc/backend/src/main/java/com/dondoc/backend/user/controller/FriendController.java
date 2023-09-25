package com.dondoc.backend.user.controller;

import com.dondoc.backend.common.exception.NotFoundException;
import com.dondoc.backend.common.utils.ApiUtils;
import com.dondoc.backend.common.utils.ApiUtils.ApiResult;
import com.dondoc.backend.user.dto.friend.FriendListDto;
import com.dondoc.backend.user.dto.friend.FriendRequestDto;
import com.dondoc.backend.user.dto.friend.FriendSearchDto;
import com.dondoc.backend.user.service.FriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@Api(value = "Friend", description = "Friend Controller", tags = "친구 API")
@RequestMapping("/api/friend")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    // 친구 요청
    @ApiOperation(value = "친구 요청", notes = "친구 요청 API", response = ApiUtils.ApiResult.class)
    @PostMapping("/request/{friendId}")
    public ApiResult friendRequest(@PathVariable @ApiParam(value = "친구 ID", required = true) Long friendId,
                @AuthenticationPrincipal UserDetails userDetails) throws Exception{
        try{
            log.info(friendId.toString());
            Long userId = Long.parseLong(userDetails.getUsername());
            FriendRequestDto.Response res = friendService.friendRequest(friendId, userId);
            return ApiUtils.success(res.getMsg());
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(NoSuchElementException ee){
            return ApiUtils.error(ee.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 친구 요청 수락
    @ApiOperation(value = "친구 요청 수락", notes = "친구 요청 수락 API", response = ApiUtils.ApiResult.class)
    @PutMapping("/request/access/{requestId}")
    public ApiResult accessRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String requestId) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            FriendRequestDto.Response res = friendService.requestAccess(userId, requestId);
            return ApiUtils.success(res.getMsg());
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(NoSuchElementException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 친구 요청 거절
    @ApiOperation(value = "친구 요청 거절", notes = "친구 요청 거절 API", response = ApiUtils.ApiResult.class)
    @PutMapping("/request/deny/{requestId}")
    public ApiResult requestDeny(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String requestId) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            FriendRequestDto.Response res = friendService.requestDeny(userId,requestId);
            return ApiUtils.success(res.getMsg());
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(NoSuchElementException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // 친구 요청 목록 조회
    @ApiOperation(value = "보낸 요청 목록 조회", notes = "보낸 요청 목록 조회 API", response = ApiUtils.ApiResult.class)
    @GetMapping("/request/send/list")
    public ApiResult sendList(@AuthenticationPrincipal UserDetails userDetails) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            FriendRequestDto.RequestListResponse res = friendService.sendList(userId);
            return ApiUtils.success(res);
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 친구 요청 목록 조회
    @ApiOperation(value = "받은 요청 목록 조회", notes = "받은 요청 목록 조회 API", response = ApiUtils.ApiResult.class)
    @GetMapping("/request/receive/list")
    public ApiResult receiveList(@AuthenticationPrincipal UserDetails userDetails) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            FriendRequestDto.RequestListResponse res = friendService.receiveList(userId);
            return ApiUtils.success(res);
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 친구 목록 조회
    @ApiOperation(value = "친구 목록 조회", notes = "친구 목록 조회 API", response = ApiUtils.ApiResult.class)
    @GetMapping("/list")
    public ApiResult friendList(@AuthenticationPrincipal UserDetails userDetails) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            FriendListDto.Response res = friendService.friendList(userId);
            return ApiUtils.success(res);
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 요청 삭제(보낸 사람이 삭제하는 것)
    @ApiOperation(value = "보낸 요청 삭제", notes = "보낸 요청 삭제 API", response = ApiUtils.ApiResult.class)
    @DeleteMapping("/request/delete/{requestId}")
    public ApiResult requestDelete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long requestId) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            FriendRequestDto.Response res = friendService.requestDelete(userId,requestId);
            return ApiUtils.success(res.getMsg());
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(NoSuchElementException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 친구 삭제
    @ApiOperation(value = "친구 삭제", notes = "친구 삭제 API", response = ApiUtils.ApiResult.class)
    @DeleteMapping("/delete/{id}")
    public ApiResult friendDelete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String id) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            FriendRequestDto.Response res = friendService.friendDelete(userId, id);
            return ApiUtils.success(res.getMsg());
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(NoSuchElementException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 친구 검색
    @ApiOperation(value = "친구 검색", notes = "친구 검색 API", response = ApiUtils.ApiResult.class)
    @GetMapping("/search/{phoneNumber}")
    public ApiResult searchFriend(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String phoneNumber) throws Exception{
        try{
            Long userId = Long.parseLong(userDetails.getUsername());
            FriendSearchDto.Response res = friendService.searchFriend(userId, phoneNumber);
            return ApiUtils.success(res);
        }catch(NotFoundException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(NoSuchElementException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
