package com.bank.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Account")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="accountId")
    private int accountId;

    @Column(name="identificationNumber", nullable = false, length = 255)
    private String identificationNumber;

    @Column(name="owner", nullable = false, length = 20)
    private String owner;

    @Column(name="accountName", nullable = false, length = 20)
    private String accountName;

    @Column(name="accountNumber", nullable = false, length = 50)
    private String accountNumber;

    @Column(name="password", nullable = false, length = 255)
    private String password;

    @Column(name="balance", nullable = false)
    private int balance;

    @Column(name="salt", nullable = false, length = 32)
    private String salt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankCodeId")
    private BankCode toCode;

    @OneToMany(mappedBy = "account")
    private List<History> historyList = new ArrayList<>();

}
