package com.dondoc.backend.user.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

public class UpdateUserDto {
    @Data
    @Builder
    @ApiModel(value = "비밀번호 변경", description = "비밀번호 변경 정보")
    public static class Request{

        @ApiModelProperty(value = "현재 비밀번호", example = "dondoc123!")
        private String password; // 비밀번호

        @ApiModelProperty(value = "새 비밀번호", example = "dondoc123!")
        private String newPassword; // 비밀번호

    }

    @Data
    @Builder
    public static class Response{
        private String msg;
        @JsonIgnore
        private boolean success;
    }
}
