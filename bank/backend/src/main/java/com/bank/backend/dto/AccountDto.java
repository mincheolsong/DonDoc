package com.bank.backend.dto;

import com.bank.backend.entity.Owner;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String ownerName;
    private Long bankCode;
    private String account;
    private String msg;
    @JsonIgnore
    private boolean success;
}
