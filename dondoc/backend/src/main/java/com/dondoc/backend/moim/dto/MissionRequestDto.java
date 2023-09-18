package com.dondoc.backend.moim.dto;

import com.dondoc.backend.moim.entity.Mission;
import com.dondoc.backend.moim.entity.MoimMember;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MissionRequestDto {

    @Data
    public static class Request {

        @NotNull(message = "회원의 ID를 입력해주세요.")
        private Long userId;

        @NotNull(message = "모임의 ID를 입력해주세요.")
        private Long moimId;

        @NotBlank(message = "사용처를 입력하세요.")
        private String title;

        @NotNull(message = "요청 금액을 입력하세요.")
        private int amount;

        @NotBlank(message = "요청 상세내용을 입력하세요.")
        private String content;

        @NotNull(message = "미션 등록 할 회원의 ID를 입력하세요.")
        private Long missionMemberId;

        @NotNull(message = "미션 종료 일자를 입력하세요.")
        private LocalDate endDate;

        @Builder
        public static Mission saveMissionRequestDto(MoimMember moimMember, String title, String content, int amount, int status, LocalDate endDate){
            return Mission.builder()
                    .moimMember(moimMember)
                    .title(title)
                    .amount(amount)
                    .status(status)
                    .content(content)
                    .endDate(endDate)
                    .build();
        }

    }

    @Builder
    @Data
    public static class Response {

        private String missionMemberName; // 미션 할 사람
        private String title; // 사용처
        private int amount; // 요청 금액
        private String content; // 요청 상세 내용
        private LocalDate endDate; // 미션 종료일자


        public static MissionRequestDto.Response toDTO(Mission entity) {
            return MissionRequestDto.Response.builder()
                    .missionMemberName(entity.getMoimMember().getUser().getName())
                    .title(entity.getTitle())
                    .amount(entity.getAmount())
                    .content(entity.getContent())
                    .endDate(entity.getEndDate())
                    .build();
        }
    }


}
