package com.dondoc.backend.moim.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

public class MissionInfoDto {

    @Builder
    @Data
    public static class Response {

        private String moimName;
        private String title;
        private String content;
        private int amount;
        private LocalDate endDate;

        public static MissionInfoDto.Response toDTO(String moimName, String title, String content, int amount, LocalDate endDate) {
            return Response.builder()
                    .moimName(moimName)
                    .title(title)
                    .content(content)
                    .amount(amount)
                    .endDate(endDate)
                    .build();
        }
    }


}
