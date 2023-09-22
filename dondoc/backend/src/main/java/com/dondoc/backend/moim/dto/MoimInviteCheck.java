package com.dondoc.backend.moim.dto;

import lombok.Getter;

public class MoimInviteCheck {

    @Getter
    public static class Request{
        private Long userId;
        private Long moimId;
        private Boolean accept;
    }
}
