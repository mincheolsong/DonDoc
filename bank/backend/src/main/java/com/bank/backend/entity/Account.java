package com.bank.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long accountId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ownerId")
    private Owner owner;

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

    @ManyToOne // Account와 BankCode는 같이 쓰이는 경우가 많아서 Eager로 뒀음
    @JoinColumn(name = "bankCodeId")
    private BankCode toCode;

    @OneToMany(mappedBy = "account")
    private List<History> historyList = new ArrayList<>();



}
