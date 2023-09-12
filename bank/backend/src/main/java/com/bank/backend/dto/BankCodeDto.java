package com.bank.backend.dto;

import com.bank.backend.entity.BankCode;
import lombok.Data;

@Data
public class BankCodeDto {

    private Long bankCodeId;
    private String bankName;

    public static BankCodeDto fromEntity(BankCode bankCode) {
        BankCodeDto dto = new BankCodeDto();
        dto.setBankCodeId(bankCode.getId());
        dto.setBankName(bankCode.getBankName());
        return dto;
    }
}