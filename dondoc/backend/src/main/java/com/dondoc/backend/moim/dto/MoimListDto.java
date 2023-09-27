package com.dondoc.backend.moim.dto;

import com.dondoc.backend.moim.entity.Moim;
import lombok.Builder;
import lombok.Data;

public class MoimListDto {

    @Builder
    @Data
    public static class Response{
        private Long moimId; // 모임ID
        private String moimName; // 모임 이름
        private String introduce; // 모임 소개글
        private int moimType; // 모임 유형 (1,2,3)
        private String identificationNumber;
        public static MoimListDto.Response toDTO(Moim entity){
            return MoimListDto.Response.builder()
                    .moimId(entity.getId())
                    .moimName(entity.getMoimName())
                    .introduce(entity.getIntroduce())
                    .moimType(entity.getMoimType())
                    .identificationNumber(entity.getIdentificationNumber())
                    .build();
        }

    }
}
