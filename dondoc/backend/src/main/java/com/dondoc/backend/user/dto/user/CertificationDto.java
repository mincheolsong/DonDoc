package com.dondoc.backend.user.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/*
*  인증을 위한 Dto => 인증번호 확인 => 응답
* */
public class CertificationDto {

    @Data
    @Builder
    @ApiModel(value = "회원 인증", description = "회원가입을 위한 인증 번호 전송")
    public static class Request{
        @ApiModelProperty(value = "인증번호", example = "000000")
        private String certificationNumber;
    }

    @Data
    @Builder
    public static class Response{
        private String msg;

        private String certificationNumber;

        private boolean success;
    }
}
