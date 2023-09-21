package com.dondoc.backend.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

public class UpdateUserDto {
    @Data
    @Builder
    @ApiModel(value = "", description = "")
    public static class Request{
        @ApiModelProperty(value = "비밀번호", example = "dondoc123!")
        private String password; // 비밀번호

        @ApiModelProperty(value = "닉네임", example = "강승현")
        private String nickName; // 닉네임

        @ApiModelProperty(value = "프로필이미지", example = "2")
        private Long imageNumber; // 이미지 번호

    }

    @Data
    @Builder
    public static class Response{
        private String msg;
        @JsonIgnore
        private boolean success;
    }
}
