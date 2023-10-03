package com.dondoc.backend.user.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public class OneRequestDto {

    @Data
    @ApiModel(value = "이미지 번호", description = "이미지 번호")
    public static class ImageNumber{
        @ApiModelProperty(value = "imageNumber", example = "1")
        private Integer imageNumber;
    }

    @Data
    @ApiModel(value = "닉네임", description = "닉네임")
    public static class NickName{
        @ApiModelProperty(value = "nickName", example = "닉네임 변경")
        private String nickName;
    }

    @Data
    @ApiModel(value = "소개글", description = "소개글")
    public static class Introduce{
        @ApiModelProperty(value = "introduce", example = "소개소개소개")
        private String introduce;
    }

    @Data
    @ApiModel(value = "계좌 ID", description = "계좌 ID")
    public static class AccountId{
        @ApiModelProperty(value = "accountId", example = "1")
        private Long accountId;
    }
}
