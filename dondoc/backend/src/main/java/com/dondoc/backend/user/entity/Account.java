package com.dondoc.backend.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Account")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="bankCode", nullable = false)
    private Long bankCode;

    @Column(name="accountId", nullable = false)
    private Long accountId;

    @Column(name="accountNumber", nullable = false, length = 50)
    private String accountNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    @JsonIgnore
    private User user;

}
