package com.dondoc.backend.user.entity;

import com.dondoc.backend.moim.entity.MoimMember;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
* phoneNumber 핸드폰 번호
* password 비밀번호
* name 이름
* email 이메일
* introduce 소개
* birth 생년월일
* nickName 별명
* refreshToken 리프레시토큰
* mainAccount 주계좌
* createdAt 회원가입 일자
* moimMemberList 속한 모임 리스트
*
* */

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

    @Column(name="birth")
    private LocalDate birth;

    @Column(name="nickName", nullable = false, length = 20)
    private String nickName;

    @Column(name="refreshToken", columnDefinition = "LONGTEXT")
    private String refreshToken;

    @Column(name="mainAccount")
    private Long mainAccount;

    @Column(name="imageNumber")
    private Long imageNumber;

    @Column(name="createdAt", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name="salt", nullable = false)
    private String salt;

    @OneToMany(mappedBy = "user")
    private List<MoimMember> moimMemberList = new ArrayList<>();

//    @OneToMany(mappedBy = "user")
//    private List<Friend> friendList = new ArrayList<>();
}
