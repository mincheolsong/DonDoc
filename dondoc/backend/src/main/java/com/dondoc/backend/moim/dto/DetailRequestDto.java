package com.dondoc.backend.moim.dto;

import com.dondoc.backend.moim.entity.WithdrawRequest;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

public class DetailRequestDto {

    @Data
    public static class Request {

        @NotNull(message = "회원의 ID를 입력해주세요.")
        private Long userId;

        @NotNull(message = "모임의 ID를 입력해주세요.")
        private Long moimId;

        @NotNull(message = "조회 할 요청타입을 입력해주세요.")
        private int requestType;

        @NotNull(message = "조회 할 요청 ID를 입력해주세요.")
        private Long requestId;
    }

    @Builder
    @Data
    public static class Response {

        private WithdrawRequestDto.Response withdrawRequest; // 출금 요청
        private MissionRequestDto.Response mission; // 미션 요청

        public static DetailRequestDto.Response toDTO_WithdrawReq(WithdrawRequestDto.Response entity) {
            return DetailRequestDto.Response.builder()
                    .withdrawRequest(entity)
                    .build();
        }
        
        public static DetailRequestDto.Response toDTO_Mission(MissionRequestDto.Response entity) {
            return DetailRequestDto.Response.builder()
                    .mission(entity)
                    .build();
        }

    }


}
