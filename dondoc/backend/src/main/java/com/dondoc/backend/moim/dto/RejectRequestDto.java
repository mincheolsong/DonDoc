package com.dondoc.backend.moim.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

public class RejectRequestDto {

    @Data
    public static class Request {

        @NotNull(message = "회원의 ID를 입력해주세요.")
        private Long userId;

        @NotNull(message = "모임의 ID를 입력해주세요.")
        private Long moimId;

        @NotNull(message = "승인 할 요청 ID를 입력해주세요.")
        private Long requestId;

    }

}
