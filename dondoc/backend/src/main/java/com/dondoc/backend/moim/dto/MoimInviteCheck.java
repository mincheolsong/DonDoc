package com.dondoc.backend.moim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

public class MoimInviteCheck {

    @ApiModel(value = "모임초대 수락 또는 거절", description = "모임초대 수락/거절에 필요한 값")
    @Getter
    public static class Request{

        @ApiModelProperty(value = "모임 ID", example = "1")
        private Long moimId;
        @ApiModelProperty(value = "모임멤버 ID", example = "15")
        private Long moimMemberId;
        @ApiModelProperty(value = "수락 또는 거절", example = "true")
        private Boolean accept;
        @ApiModelProperty(value = "연결할 계좌 accountId(dondoc 서비스)", example = "2")
        private Long accountId;

    }
}
