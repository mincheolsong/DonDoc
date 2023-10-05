package com.dondoc.backend.user.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

public class SignUpDto {


    @Data
    @Builder
    @ApiModel(value = "회원가입 요청 Dto", description = "회원가입을 위한 기본 정보")
    public static class Request{
        @ApiModelProperty(value = "전화번호", example = "01026807453")
        private String phoneNumber; // 전화번호(아이디)

        @ApiModelProperty(value = "비밀번호", example = "dondoc123!")
        private String password; // 비밀번호

        @ApiModelProperty(value = "이름", example = "강승현")
        private String name; // 이름

        @ApiModelProperty(value = "별명", example = "강승현")
        private String nickName; // 별명

        @ApiModelProperty(value = "인증여부", example = "true")
        private boolean certification; // 인증여부
    }

    @Data
    @Builder
    public static class Response{
        private String msg;

        @JsonIgnore
        private boolean success;
    }
}
