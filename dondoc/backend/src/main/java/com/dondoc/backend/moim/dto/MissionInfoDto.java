package com.dondoc.backend.moim.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

public class MissionInfoDto {

    @Builder
    @Data
    public static class Response {

        private Long id;
        private Long moimId;
        private String moimName;
        private String title;
        private String content;
        private int amount;
        private LocalDate endDate;

        public static MissionInfoDto.Response toDTO(Long id, Long moimId, String moimName, String title, String content, int amount, LocalDate endDate) {
            return Response.builder()
                    .id(id)
                    .moimId(moimId)
                    .moimName(moimName)
                    .title(title)
                    .content(content)
                    .amount(amount)
                    .endDate(endDate)
                    .build();
        }
    }


}
