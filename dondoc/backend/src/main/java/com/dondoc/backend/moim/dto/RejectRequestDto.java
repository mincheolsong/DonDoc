package com.dondoc.backend.moim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class RejectRequestDto {

    @ApiModel(value = "요청 거절", description = "요청 거절에 사용하는 request DTO")
    @Data
    public static class Request {

        @ApiModelProperty(value = "모임 ID", example = "1")
        @NotNull(message = "모임의 ID를 입력해주세요.")
        private Long moimId;

        @ApiModelProperty(value = "승인 할 요청 ID", example = "1")
        @NotNull(message = "승인 할 요청 ID를 입력해주세요.")
        private Long requestId;

    }

}
