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
        private int userType; // 이 모임에 대한 권한 (0 : 관리자, 1:사용자)
        private Long accountId; // 내가 연결한 accountId

        public static MoimListDto.Response toDTO(Moim entity){
            return Response.builder()
                    .moimId(entity.getId())
                    .moimName(entity.getMoimName())
                    .introduce(entity.getIntroduce())
                    .moimType(entity.getMoimType())
                    .identificationNumber(entity.getIdentificationNumber())
                    .userType(entity.getMoimMemberList().get(0).getUserType())
                    .accountId(entity.getMoimMemberList().get(0).getAccount().getAccountId())
                    .build();
        }

    }
}
