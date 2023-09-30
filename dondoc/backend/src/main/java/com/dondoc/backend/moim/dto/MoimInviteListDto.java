package com.dondoc.backend.moim.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class MoimInviteListDto {

    @Data
    public static class Response{
        private Long moimId;
        private Long moimMemberId;
        private String moimName; // 모임이름
        private String moimType; // 모임계좌 유형
        private String introduce; // 모임 소개글
        private String inviter; // 초대한 사람이름

        public Response(Long moimId, Long moimMemberId,String moimName,String moimType,String introduce,String inviter) {
            this.moimId = moimId;
            this.moimMemberId = moimMemberId;
            this.moimName = moimName;
            this.moimType=moimType;
            this.introduce=introduce;
            this.inviter=inviter;
        }
    }


}
