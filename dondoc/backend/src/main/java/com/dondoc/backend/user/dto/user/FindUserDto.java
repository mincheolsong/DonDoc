package com.dondoc.backend.user.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

public class FindUserDto {
    @Data
    @Builder
    @ApiModel(value = "사용자 검색", description = "핸드폰 번호를 통한 사용자 검색")
    public static class Request{
        @ApiModelProperty(value = "유저 아이디(핸드폰번호)", example = "01026807453")
        private String phoneNumber; // 변수 설명

    }

    @Data
    @Builder
    public static class Response{
        private Long userId;
        private String phoneNumber;
        private String NickName;
        private Long imageNumber;
        private String msg;
        @JsonIgnore
        private boolean success;
    }
}
