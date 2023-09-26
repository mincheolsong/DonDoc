package com.dondoc.backend.moim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MoimInviteDto {

    @ApiModel(value = "모임 초대", description = "모임 초대에 필요한 request DTO")
    @Data
    public static class Request{
        @ApiModelProperty(value = "모임테이블의 ID", example = "1")
        private Long moimId;
        @ApiModelProperty(value = "모임에 초대할 userId",example = "[{\"userId\": 5}, {\"userId\": 4}]")
        private List<InviteDto> invite = new ArrayList<>();
    }

    @Getter
    public static class InviteDto{
        private Long userId;
    }
}
