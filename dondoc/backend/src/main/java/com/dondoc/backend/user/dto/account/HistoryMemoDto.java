package com.dondoc.backend.user.dto.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

public class HistoryMemoDto {

    @Data
    @Builder
    @ApiModel(value = "메모 작성", description = "메모 작성 API")
    public static class Request{
        @ApiModelProperty(value = "계좌번호", example = "5300334585926")
        private String accountNumber;
        @ApiModelProperty(value = "메모", example = "메모")
        private String content;
        @ApiModelProperty(value = "거래내역 ID", example = "1")
        private Long historyId;
    }

    @Data
    @Builder
    public static class Response{
        private boolean success;
        private String msg;
        private Map result;
    }
}
