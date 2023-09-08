package com.bank.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountListRequestDto {
    private List<String> identificationNumber;

}
