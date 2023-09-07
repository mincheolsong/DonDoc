package com.bank.backend.entity;

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
    @Column(name="historyId")
    private int historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="accountId")
    private Account account;

    @Column(name="toAccount", nullable = false, length = 50)
    private String toAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankCodeId")
    private BankCode toCode;

    @Column(name="type", nullable = false)
    private int type;

    @Column(name="transferAmount", nullable = false)
    private int transferAmount;

    @Column(name="afterBalance", nullable = false)
    private int afterBalance;

    @Column(name="memo", length = 255)
    private String memo;

    @Column(name="toMemo", length = 255)
    private String toMemo;

    @Column(name="createdAt", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

}
