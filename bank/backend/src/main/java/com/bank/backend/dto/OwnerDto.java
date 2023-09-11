package com.bank.backend.dto;

import com.bank.backend.entity.Owner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

public class OwnerDto {

    @ApiModel(value = "예금주 생성", description = "예금주 생성에 필요한 요청값 DTO")
    @Data
    @Builder
    public static class Request{
        @ApiModelProperty(value = "회원이름", example = "강승현") // example 속성으로 초기값을 설정할 수 있음
        private String ownerName;
        @ApiModelProperty(value = "식별번호", example = "01026807453")
        private String identificationNumber;
    }

    @Builder
    @Data
    public static class Response{
        private Owner owner;
        @JsonIgnore
        private boolean isPresent;
    }
}
