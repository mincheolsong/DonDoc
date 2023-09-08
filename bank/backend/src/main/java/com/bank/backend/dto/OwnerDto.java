package com.bank.backend.dto;

import lombok.Builder;
import lombok.Data;

public class OwnerDto {

    @Data
    @Builder
    public static class Request{
        private String ownerName;
        private String identificationNumber;
    }

}
