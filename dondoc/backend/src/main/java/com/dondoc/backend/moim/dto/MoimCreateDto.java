package com.dondoc.backend.moim.dto;

import com.dondoc.backend.moim.entity.Moim;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

public class MoimCreateDto {

    @Data
    public static class Request{
        private String moimName; // 모임 이름
        private String introduce; // 소개글
        private int moimType;
        private String password; // 비밀번호
    }

    @Data
    @Builder
    public static class Response{
        private String msg;
        private Long moimId;
        private String identificationNumber; // 식별번호
        private String moimName; // 모임이름
        private String introduce; // 소개
        private String moimAccount; // 모임계좌
        private int moimType; // 모임타입
        public static MoimCreateDto.Response toDTO(Moim entity) {
            return Response.builder()
                    .msg("모임 생성 성공")
                    .moimId(entity.getId())
                    .identificationNumber(entity.getIdentificationNumber())
                    .moimName(entity.getMoimName())
                    .introduce(entity.getIntroduce())
                    .moimAccount(entity.getMoimAccount())
                    .moimType(entity.getMoimType())
                    .build();
        }
    }

}
