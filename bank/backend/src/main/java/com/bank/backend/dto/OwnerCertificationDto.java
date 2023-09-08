package com.bank.backend.dto;

import com.bank.backend.entity.Owner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;


public class OwnerCertificationDto {

    @Builder
    @Data
    public static class Response{
        private Owner owner;
        @JsonIgnore
        private boolean isPresent;
    }
}


