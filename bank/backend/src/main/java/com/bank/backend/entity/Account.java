package com.bank.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name="wrongCount", nullable = false)
    private int wrongCount;

    @Column(name="status", nullable = false)
    private boolean status;

    @JsonIgnore // JSON Serialization 오류 해결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankCodeId")
    private BankCode bankCode;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    private List<History> historyList = new ArrayList<>();

    @PrePersist
    public void prePersist(){
        this.wrongCount = 0;
        this.status = true;
    }


}
