package com.dondoc.backend.common.exception;

import com.dondoc.backend.common.utils.ApiUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilterException {
    public String isRefreshExpired() throws JsonProcessingException {
        return createJson("토큰이 만료되었습니다. 재 로그인 해주세요.");
    }

    public String noAuthentication() throws JsonProcessingException {
        return createJson("인증정보가 유효하지않습니다. 재 로그인 해주세요.");
    }

    public String noRefreshToken() throws JsonProcessingException {
        return createJson("refreshToken이 존재하지 않습니다. 재 로그인 해주세요.");
    }

    public String createJson(String message) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(ApiUtils.error("refreshToken이 존재하지 않습니다. 재 로그인 해주세요.", HttpStatus.UNAUTHORIZED));
    }
}
