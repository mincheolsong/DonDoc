package com.dondoc.backend.moim.dto;

import com.dondoc.backend.moim.entity.Category;
import com.dondoc.backend.moim.entity.MoimMember;
import com.dondoc.backend.moim.entity.WithdrawRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class WithdrawRequestDto {

    @ApiModel(value = "출금 요청", description = "출금 요청에 사용하는 request DTO")
    @Data
    public static class Request {

        @ApiModelProperty(value = "모임 ID", example = "1")
        @NotNull(message = "모임의 ID를 입력해주세요.")
        private Long moimId;

        @ApiModelProperty(value = "사용처", example = "옷 사고싶어요")
        @NotBlank(message = "사용처를 입력하세요.")
        private String title;

        @ApiModelProperty(value = "요청 금액", example = "14000")
        @NotNull(message = "요청 금액을 입력하세요.")
        private int amount;

        @ApiModelProperty(value = "요청 상세내용", example = "맨투맨 살래요")
        @NotBlank(message = "요청 상세내용을 입력하세요.")
        private String content;

        @ApiModelProperty(value = "카테고리 ID", example = "2")
        @NotNull(message = "카테고리의 ID를 입력하세요.")
        private Long categoryId;

        @Builder
        public static WithdrawRequest saveWithdrawRequestDto(MoimMember moimMember, String title, String content, Category category, int amount, int status){
            return WithdrawRequest.builder()
                    .moimMember(moimMember)
                    .title(title)
                    .category(category)
                    .amount(amount)
                    .status(status)
                    .content(content)
                    .build();
        }
    }

    @Builder
    @Data
    public static class Response {

        private Long withdrawId;
        private String moimMemberName; // 신청인
        private String title; // 사용처
        private int amount; // 요청 금액
        private String content; // 요청 상세 내용
        private Category category; // 카테고리
        private int status; // 상태
        private int imageNumber;

        public static WithdrawRequestDto.Response toDTO(WithdrawRequest entity) {
            return Response.builder()
                    .withdrawId(entity.getId())
                    .moimMemberName(entity.getMoimMember().getUser().getName())
                    .title(entity.getTitle())
                    .amount(entity.getAmount())
                    .content(entity.getContent())
                    .status(entity.getStatus())
                    .category(entity.getCategory())
                    .imageNumber(entity.getMoimMember().getUser().getImageNumber())
                    .build();
        }
    }

}
