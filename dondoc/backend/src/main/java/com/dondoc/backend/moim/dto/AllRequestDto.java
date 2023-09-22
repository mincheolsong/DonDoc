package com.dondoc.backend.moim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AllRequestDto {

    @ApiModel(value = "요청 리스트 조회", description = "요청 리스트 조회에 사용하는 request DTO")
    @Data
    public static class Request {

        @ApiModelProperty(value = "모임 ID", example = "1")
        @NotNull(message = "모임의 ID를 입력해주세요.")
        private Long moimId;

        @ApiModelProperty(value = "조회 할 회원 ID", example = "1")
        private Long findUserId;

    }

    @Builder
    @Data
    public static class Response {

        private List<WithdrawRequestDto.Response> withdrawRequestList; // 출금 요청
        private List<MissionRequestDto.Response> missionList; // 미션 요청

        public static AllRequestDto.Response toDTO(List<WithdrawRequestDto.Response> withdrawRequestList, List<MissionRequestDto.Response> missionList) {
            return Response.builder()
                    .withdrawRequestList(withdrawRequestList)
                    .missionList(missionList)
                    .build();
        }
    }


}
