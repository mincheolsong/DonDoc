package com.dondoc.backend.user.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

public class ProfileDto {
    @Data
    @Builder
    public static class Response{
        private int imageNumber;
        private String name;
        private String introduce;
        private Long bankCode;
        private String account;
        private LocalDate birth;
        private String phoneNumber;
        private boolean mine;
        private String msg;
        @JsonIgnore
        private boolean success;
    }
}
