package com.dondoc.backend.moim.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class MoimInviteListDto {

    @Data
    public static class Response{
        private Long moimId;
        private Long moimMemberId;

        public Response(Long moimId, Long moimMemberId) {
            this.moimId = moimId;
            this.moimMemberId = moimMemberId;
        }
    }


}
