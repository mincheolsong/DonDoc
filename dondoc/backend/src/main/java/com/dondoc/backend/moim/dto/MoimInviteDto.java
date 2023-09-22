package com.dondoc.backend.moim.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

public class MoimInviteDto {

    @Data
    public static class Request{
        private Long moimId;
        private List<InviteDto> invite;
    }

    @Getter
    public class InviteDto{
        private Long userId;
    }
}
