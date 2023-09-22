package com.dondoc.backend.moim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class CancelRequestDto {

    @ApiModel(value = "요청 취소", description = "요청 취소에 사용하는 request DTO")
    @Data
    public static class Request {

        @ApiModelProperty(value = "모임 ID", example = "1")
        @NotNull(message = "모임의 ID를 입력해주세요.")
        private Long moimId;

        @ApiModelProperty(value = "조회 할 요청타입", example = "1")
        @NotNull(message = "조회 할 요청타입을 입력해주세요.")
        private int requestType;

        @ApiModelProperty(value = "취소 할 요청 ID", example = "1")
        @NotNull(message = "취소 할 요청 ID를 입력해주세요.")
        private Long requestId;

    }

    @Builder
    @Data
    public static class Response {

        private String msg;
        private String moimName;
        private String title;
        private String content;
        private int amount;

        public static CancelRequestDto.Response toDTO(String msg, String moimName, String title, String content, int amount) {
            return CancelRequestDto.Response.builder()
                    .msg(msg)
                    .moimName(moimName)
                    .title(title)
                    .content(content)
                    .amount(amount)
                    .build();
        }

    }

}
