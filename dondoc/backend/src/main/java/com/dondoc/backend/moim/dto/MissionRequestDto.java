package com.dondoc.backend.moim.dto;

import com.dondoc.backend.moim.entity.Mission;
import com.dondoc.backend.moim.entity.MoimMember;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MissionRequestDto {

    @ApiModel(value = "미션 요청", description = "미션 요청에 사용하는 request DTO")
    @Data
    public static class Request {

        @ApiModelProperty(value = "모임 ID", example = "1")
        @NotNull(message = "모임의 ID를 입력해주세요.")
        private Long moimId;

        @ApiModelProperty(value = "미션명", example = "취업하자")
        @NotBlank(message = "미션명을 입력하세요.")
        private String title;

        @ApiModelProperty(value = "요청 금액", example = "10000")
        @NotNull(message = "요청 금액을 입력하세요.")
        private int amount;

        @ApiModelProperty(value = "요청 상세내용", example = "매일 5시간 공부")
        @NotBlank(message = "요청 상세내용을 입력하세요.")
        private String content;

        @ApiModelProperty(value = "미션 등록 할 회원 ID", example = "1")
        @NotNull(message = "미션 등록 할 회원의 ID를 입력하세요.")
        private Long missionMemberId;

        @ApiModelProperty(value = "미션 종료 일자", example = "2023-10-02")
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

        @Builder
        public static Mission saveMissionRequestDto(MoimMember moimMember, String title, String content, int amount, int status,
                                                    LocalDateTime startDate, LocalDate endDate){
            return Mission.builder()
                    .moimMember(moimMember)
                    .title(title)
                    .amount(amount)
                    .status(status)
                    .content(content)
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();
        }
    }

    @Builder
    @Data
    public static class Response {

        private Long missionId;
        private String missionMemberName; // 미션 할 사람
        private String title; // 미션명
        private int amount; // 요청 금액
        private String content; // 요청 상세 내용
        private LocalDate endDate; // 미션 종료일자
        private int status; // 상태
        private int imageNumber;


        public static MissionRequestDto.Response toDTO(Mission entity) {
            return Response.builder()
                    .missionId(entity.getId())
                    .missionMemberName(entity.getMoimMember().getUser().getName())
                    .title(entity.getTitle())
                    .amount(entity.getAmount())
                    .content(entity.getContent())
                    .status(entity.getStatus())
                    .endDate(entity.getEndDate())
                    .imageNumber(entity.getMoimMember().getUser().getImageNumber())
                    .build();
        }
    }


}
