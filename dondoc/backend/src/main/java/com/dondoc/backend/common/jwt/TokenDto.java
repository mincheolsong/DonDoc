package com.dondoc.backend.common.jwt;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenDto {
    private Long userId;
    private String phoneNumber;
    private String name;
}
