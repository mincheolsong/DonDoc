package com.dondoc.backend.user.dto.friend;

import com.dondoc.backend.user.entity.Friend;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

public class FriendSearchDto {

    @Data
    @Builder
    public static class Response{
        private String msg;
        private FriendInfo friend;
        @JsonIgnore
        private boolean success;
    }

    @Data
    @Builder
    public static class FriendInfo{
        private Long id;
        private String name;
        private String imageNumber;
        private String phoneNumber;
        private String bankName;
        private Long bankCode;
        private String accountNumber;
    }
}
