package com.dondoc.backend.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

public class FindPasswordDto {
    @Data
    @Builder
    @ApiModel(value = "사용자 검색", description = "핸드폰 번호를 통한 사용자 검색")
    public static class Request{

        @ApiModelProperty(value = "인증여부", example = "true")
        private boolean certification; // 변수 설명

        @ApiModelProperty(value = "비밀번호", example = "dondoc123!")
        private String password; // 변수 설명

        @ApiModelProperty(value = "유저 아이디(핸드폰번호)", example = "01026807453")
        private String phoneNumber; // 변수 설명

    }

    @Data
    @Builder
    public static class Response{
        private String msg;
        @JsonIgnore
        private boolean success;
    }
}
