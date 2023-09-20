package com.dondoc.backend.moim.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class AllowRequestDto {

    @Data
    public static class Request {

        @NotNull(message = "회원의 ID를 입력해주세요.")
        private Long userId;

        @NotNull(message = "모임의 ID를 입력해주세요.")
        private Long moimId;

        @NotNull(message = "승인 할 요청 ID를 입력해주세요.")
        private Long requestId;

        @NotNull(message = "비밀번호를 입력해주세요.")
        private String password;
    }

    @Builder
    @Data
    public static class Response {

        private String msg;
        private WithdrawRequestDto.Response withdrawRequest;
        private MissionRequestDto.Response mission;

        public static AllowRequestDto.Response toDTO_WithdrawReq(String msg, WithdrawRequestDto.Response entity) {
            return AllowRequestDto.Response.builder()
                    .msg(msg)
                    .withdrawRequest(entity)
                    .build();
        }

        public static AllowRequestDto.Response toDTO_Mission(String msg, MissionRequestDto.Response entity) {
            return AllowRequestDto.Response.builder()
                    .msg(msg)
                    .mission(entity)
                    .build();
        }
    }
}
