package com.bank.backend.entity;

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
    @Column(name="historyId")
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="accountId")
    private Account account;

    @Column(name="toAccount", nullable = false, length = 50)
    private String toAccount;

    @JsonIgnore
    @ManyToOne
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
