package com.bank.backend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="History")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="accountId")
    private Account account;

    @Column(name="toAccount", nullable = false, length = 50)
    private String toAccount;


    @JsonIgnore // JSON Serialization 오류 해결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankCodeId")
    private BankCode toCode;

    @Column(name="type", nullable = false)
    private int type;

    @Column(name="transferAmount", nullable = false)
    private int transferAmount;

    @Column(name="afterBalance", nullable = false)
    private int afterBalance;

    @Column(name="sign", length = 255)
    private String sign;

    @Column(name="toSign", length = 255)
    private String toSign;

    @Column(name="createdAt", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

}
