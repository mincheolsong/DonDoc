package com.dondoc.backend.moim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class SuccessOrNotMissionDto {

    @ApiModel(value = "미션 성공", description = "미션 성공에 사용하는 request DTO")
    @Data
    public static class Request {

        @ApiModelProperty(value = "회원 ID")
        @NotNull(message = "회원의 ID를 입력해주세요.")
        private Long userId;

        @ApiModelProperty(value = "모임 ID")
        @NotNull(message = "모임의 ID를 입력해주세요.")
        private Long moimId;

        @ApiModelProperty(value = "승인 할 요청 ID")
        @NotNull(message = "승인 할 요청 ID를 입력해주세요.")
        private Long requestId;

        @ApiModelProperty(value = "비밀번호")
        @NotNull(message = "비밀번호를 입력해주세요.")
        private String password;
    }

    @Builder
    @Data
    public static class Response {

        private String msg;
        private String moimName;
        private String title;
        private String content;
        private int amount;
        private LocalDate endDate;

        public static SuccessOrNotMissionDto.Response toDTO(String msg, String moimName, String title, String content, int amount, LocalDate endDate) {
            return SuccessOrNotMissionDto.Response.builder()
                    .msg(msg)
                    .moimName(moimName)
                    .title(title)
                    .content(content)
                    .amount(amount)
                    .endDate(endDate)
                    .build();
        }

    }
}
