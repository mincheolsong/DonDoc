package com.dondoc.backend.moim.dto;

import com.dondoc.backend.moim.entity.Category;
import com.dondoc.backend.moim.entity.MoimMember;
import com.dondoc.backend.moim.entity.WithdrawRequest;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class WithdrawRequestDto {

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

        @NotNull(message = "카테고리의 ID를 입력하세요.")
        private Long categoryId;

        @Builder
        public static WithdrawRequest saveWithdrawRequestDto(MoimMember moimMember, String title, String content,
                                                             Category category, int amount, int status){
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

        private String moimMemberName; // 신청인
        private String title; // 사용처
        private int amount; // 요청 금액
        private String content; // 요청 상세 내용
        private Category category; // 카테고리
        

        public static WithdrawRequestDto.Response toDTO(WithdrawRequest entity) {
            return Response.builder()
                    .moimMemberName(entity.getMoimMember().getUser().getName())
                    .title(entity.getTitle())
                    .amount(entity.getAmount())
                    .content(entity.getContent())
                    .category(entity.getCategory())
                    .build();
        }
    }

}
