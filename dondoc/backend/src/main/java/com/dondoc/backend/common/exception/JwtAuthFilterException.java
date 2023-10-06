package com.dondoc.backend.common.exception;

import com.dondoc.backend.common.utils.ApiUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilterException {

    public String isRefreshExpired() throws JsonProcessingException {
        return createJson("모든 인증정보가 만료되었습니다. 재 로그인 해주세요.");
    }

    public String noAuthentication() throws JsonProcessingException {
        return createJson("인증정보가 존재하지 않습니다. 재 로그인 해주세요.");
    }

    public String noRefreshToken() throws JsonProcessingException {
        return createJson("인증정보가 존재하지 않습니다. 재 로그인 해주세요.");
    }

    public String createJson(String message) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(ApiUtils.error(message, HttpStatus.UNAUTHORIZED));
    }

    public String black() throws JsonProcessingException{
        return createJson("인증정보가 유효하지 않습니다. 다시 로그인 해주세요.");
    }
}
