package com.dondoc.backend.user.dto.user;

import com.dondoc.backend.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.servlet.http.Cookie;

public class SignInDto {

    @Data
    @Builder
    @ApiModel(value = "로그인 요청 Dto", description = "로그인을 위한 정보")
    public static class Request{
        @ApiModelProperty(value = "아이디", example = "01026807453")
        private String phoneNumber;

        @ApiModelProperty(value = "비밀번호", example = "dondoc123!")
        private String password;

    }

    @Data
    @Builder
    public static class Response{
        private String msg;

        private String phoneNumber;
        private String name;
        private String introduce;
        private String birth;
        private String nickname;
        private String mainAccount;
        private int imageNumber;

        private String accessToken;

        @JsonIgnore
        private Cookie refreshToken;

        @JsonIgnore
        private boolean success;
    }
}
