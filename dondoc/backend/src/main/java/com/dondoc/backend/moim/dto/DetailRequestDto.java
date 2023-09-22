package com.dondoc.backend.moim.dto;

import com.dondoc.backend.moim.entity.WithdrawRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class DetailRequestDto {

    @ApiModel(value = "요청 상세조회", description = "요청 상세조회에 사용하는 request DTO")
    @Data
    public static class Request {

        @ApiModelProperty(value = "모임 ID", example = "1")
        @NotNull(message = "모임의 ID를 입력해주세요.")
        private Long moimId;

        @ApiModelProperty(value = "조회 할 요청타입", example = "1")
        @NotNull(message = "조회 할 요청타입을 입력해주세요.")
        private int requestType;

        @ApiModelProperty(value = "조회 할 요청 ID", example = "1")
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
