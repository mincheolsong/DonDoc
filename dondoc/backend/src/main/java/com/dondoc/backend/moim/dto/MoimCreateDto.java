package com.dondoc.backend.moim.dto;

import com.dondoc.backend.moim.entity.Moim;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

public class MoimCreateDto {

    @ApiModel(value="모임 생성", description="모임 생성에 필요한 Request Dto")
    @Data
    public static class Request{
        @ApiModelProperty(value = "모임 이름", example = "돈독모임")
        private String moimName; // 모임 이름

        @ApiModelProperty(value = "소개글", example = "안녕하세요")
        private String introduce; // 소개글

        @ApiModelProperty(value = "모임 종류 (1,2,3)", example = "1")
        private int moimType; //

        @ApiModelProperty(value = "비밀번호", example = "1234")
        private String password; // 비밀번호

        @ApiModelProperty(value = "연결할 계좌의 id (계좌 테이블의 id값)", example = "1")
        private Long accountId; // 연결할 계좌의 id (계좌 테이블의 id값)

        @ApiModelProperty(value = "관리자로 초대하는 회원id 정보",example = "[{\"userId\": 5}, {\"userId\": 4}]")
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
    public static class InviteDto{
        private Long userId;
    }

}
