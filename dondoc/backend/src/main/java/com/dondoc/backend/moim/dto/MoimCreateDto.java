package com.dondoc.backend.moim.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

public class MoimCreateDto {

    @Data
    public static class Request{
        private String moimName; // 모임 이름
        private String introduce; // 소개글
        private Long password; // 비밀번호
    }

    @Data
    public static class Response{
        private String identificationNumber; // 식별번호
        private String moimName; // 모임이름
        private String introduce; // 소개
        private String moimAccount; // 모임계좌
        private String moimType; // 모임타입
    }

}
