package com.dondoc.backend.user.dto.friend;

import com.dondoc.backend.user.entity.Friend;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

public class FriendListDto {
    @Data
    @Builder
    public static class Response{
        private List<FriendInfoDto> list;
        private String msg;
        @JsonIgnore
        private boolean success;
    }

    @Data
    @Builder
    public static class FriendInfoDto {
        private Long id;
        private Long friendId; // 상대
        private LocalDateTime createdAt;
    }

}