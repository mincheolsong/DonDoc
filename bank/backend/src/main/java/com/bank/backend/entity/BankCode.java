package com.bank.backend.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="BankCode")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankCode {

    @Id
    @Column(name="bankCodeId")
    private int bankCodeId;

    @Column(name="bankName", nullable = false, length = 20)
    private String bankName;

}
