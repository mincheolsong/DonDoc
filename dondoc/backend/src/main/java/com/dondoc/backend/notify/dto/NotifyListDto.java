package com.dondoc.backend.notify.dto;

import com.dondoc.backend.moim.dto.MoimListDto;
import com.dondoc.backend.moim.entity.Moim;
import com.dondoc.backend.notify.entity.Notify;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class NotifyListDto {

    @Builder
    @Data
    public static class Response{
        private Long notifyId; // 모임ID
        private String title; // 모임 이름
        private String content; // 모임 소개글
        private int type; // 모임 유형 (1,2,3)
        private LocalDateTime createdAt;

        public static NotifyListDto.Response toDTO(Notify entity){
            return Response.builder()
                    .notifyId(entity.getId())
                    .title(entity.getTitle())
                    .content(entity.getContent())
                    .type(entity.getNotifyType())
                    .createdAt(entity.getCreatedAt())
                    .build();
        }
    }
}
