package com.dondoc.backend.moim.dto;

import com.dondoc.backend.moim.entity.Moim;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

public class MoimCreateDto {

    @Data
    public static class Request{
        private String moimName; // 모임 이름
        private String introduce; // 소개글
        private int moimType; // 모임 종류 (1,2,3)
        private String password; // 비밀번호
        private Long accountId; // 연결할 계좌의 id (계좌 테이블의 id값)
        private List<InviteDto> manager; // 관리자로 초대하는 회원id 정보
    }

    @Data
    @Builder
    public static class Response{
        private String msg;
        private Long moimId;
        private String identificationNumber; // 식별번호
        private String moimName; // 모임이름
        private String introduce; // 소개
        private String moimAccountNumber; // 모임계좌
        private int moimType; // 모임타입
        public static MoimCreateDto.Response toDTO(Moim entity) {
            return Response.builder()
                    .msg("모임 생성 성공")
                    .moimId(entity.getId())
                    .identificationNumber(entity.getIdentificationNumber())
                    .moimName(entity.getMoimName())
                    .introduce(entity.getIntroduce())
                    .moimAccountNumber(entity.getMoimAccountNumber())
                    .moimType(entity.getMoimType())
                    .build();
        }
    }

    @Getter
    public class InviteDto{
        private Long userId;
    }

}
