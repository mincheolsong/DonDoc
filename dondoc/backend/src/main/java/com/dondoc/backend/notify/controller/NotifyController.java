package com.dondoc.backend.notify.controller;

import com.dondoc.backend.common.utils.ApiUtils;
import com.dondoc.backend.moim.dto.MoimListDto;
import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.notify.dto.NotifyListDto;
import com.dondoc.backend.notify.entity.Notify;
import com.dondoc.backend.notify.service.NotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "Notify", description = "알림 컨트롤러", tags = "알림 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notify")
public class NotifyController {

    private final NotifyService notifyService;

    @ApiOperation(value = "알림 리스트", notes = "알림 리스트를 가져오는 API", response = ApiUtils.ApiResult.class)
    @GetMapping("/list") // 헤더의 토큰을 이용해서 가져온 userId를 이용해서 알림 List를 조회
    public ApiUtils.ApiResult getMoimList(Authentication authentication){
        // 현재 로그인한 User 엔티티 찾기 (token 헤더값에서 userId가져오기)
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        List<NotifyListDto.Response> result = new ArrayList<>();

        try {
            String userId = userDetails.getUsername();
            List<Notify> notifyList = notifyService.getNotifyList(Long.parseLong(userId));
            for(Notify n : notifyList){ // 엔티티를 dto로 변환
                result.add(NotifyListDto.Response.toDTO(n));
            }
            return ApiUtils.success(result);
        }catch (Exception e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
