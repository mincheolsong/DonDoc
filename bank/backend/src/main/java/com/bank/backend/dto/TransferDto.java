package com.bank.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {
    private String msg;

    @JsonIgnore
    private boolean success;
}
