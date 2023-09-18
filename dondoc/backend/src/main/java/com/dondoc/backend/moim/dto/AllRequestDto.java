package com.dondoc.backend.moim.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AllRequestDto {

    @Data
    public static class Request {

        @NotNull(message = "회원의 ID를 입력해주세요.")
        private Long userId;

        @NotNull(message = "모임의 ID를 입력해주세요.")
        private Long moimId;

    }

    @Builder
    @Data
    public static class Response {

        private List<WithdrawRequestDto.Response> withdrawRequestList; // 출금 요청
        private List<MissionRequestDto.Response> missionList; // 출금 요청

        public static AllRequestDto.Response toDTO(List<WithdrawRequestDto.Response> withdrawRequestList, List<MissionRequestDto.Response> missionList) {
            return Response.builder()
                    .withdrawRequestList(withdrawRequestList)
                    .missionList(missionList)
                    .build();
        }
    }


}
