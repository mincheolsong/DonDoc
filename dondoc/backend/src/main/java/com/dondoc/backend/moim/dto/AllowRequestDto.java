package com.dondoc.backend.moim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class AllowRequestDto {

    @ApiModel(value = "요청 승인", description = "요청 승인에 사용하는 request DTO")
    @Data
    public static class Request {

        @ApiModelProperty(value = "모임 ID", example = "1")
        @NotNull(message = "모임의 ID를 입력해주세요.")
        private Long moimId;

        @ApiModelProperty(value = "승인 할 요청 ID", example = "1")
        @NotNull(message = "승인 할 요청 ID를 입력해주세요.")
        private Long requestId;

        @ApiModelProperty(value = "비밀번호", example = "1234 || dondoc123!")
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
