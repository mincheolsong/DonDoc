package com.dondoc.backend.moim.entity;

import com.dondoc.backend.user.entity.Account;
import com.dondoc.backend.user.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="MoimMember")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MoimMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="moimId")
    private Moim moim;

    @Column(name="userType", nullable = false)
    private int userType;

    @Column(name="status", nullable = false)
    private int status;

    @Column(name="invitedAt", updatable = false)
    @CreatedDate
    private LocalDateTime invitedAt;

    @Column(name="signedAt")
    @LastModifiedDate
    private LocalDateTime signedAt;

    @OneToOne
    @JoinColumn(name = "accountId")
    private Account account;

}
