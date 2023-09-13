package com.dondoc.backend.user.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="User")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="phoneNumber", nullable = false, length = 255)
    private String phoneNumber;

    @Column(name="password", nullable = false, length = 255)
    private String password;

    @Column(name="name", nullable = false, length = 20)
    private String name;

    @Column(name="email", nullable = false, length = 50)
    private String email;

    @Column(name="introduce", columnDefinition = "LONGTEXT")
    private String introduce;

    @Column(name="birth", nullable = false)
    private LocalDate birth;

    @Column(name="nickName", nullable = false, length = 20)
    private String nickName;

    @Column(name="refreshToken", columnDefinition = "LONGTEXT")
    private String refreshToken;

    @Column(name="mainAccount")
    private int mainAccount;

    @Column(name="createdAt", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

}
