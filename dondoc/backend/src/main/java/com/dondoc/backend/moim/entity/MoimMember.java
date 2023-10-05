package com.dondoc.backend.moim.entity;

import com.dondoc.backend.user.entity.Account;
import com.dondoc.backend.user.entity.User;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="MoimMember")
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MoimMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    // 양방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    // 양방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="moimId")
    private Moim moim;

    // 양방향
    // 일대다 fetch join 을 두 번 사용할 수 없어서 BatchSize로 해결 (여러 MoimMember 객체가 getWithdrawRequest()를 호출할 때 하나의 쿼리(in)로 가져옴)
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "moimMember",cascade = CascadeType.REMOVE)
    private List<WithdrawRequest> withdrawRequests = new ArrayList<>();
    /**
     * 0 : 관리자
     * 1 : 회원
     **/
    @Column(name="userType", nullable = false)
    private int userType;

    /**
     * 0 : 승인 대기중
     * 1 : 승인
     **/
    @Column(name="status", nullable = false)
    private int status;

    @Column(name="invitedAt", updatable = false)
    @CreatedDate
    private LocalDateTime invitedAt;

    @Column(name="signedAt")
    @LastModifiedDate
    private LocalDateTime signedAt;

    // 단방향
    @OneToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @Column(name = "inviterName",length = 20)
    private String inviterName;

    public MoimMember(int userType, int status,String inviterName){
        this.userType=userType;
        this.status=status;
        this.inviterName=inviterName;
    }
    public MoimMember(int userType, int status, LocalDateTime signedAt, Account account) {
        this.userType = userType;
        this.status = status;
        this.signedAt = signedAt;
        this.account = account;
    }

    public MoimMember(int userType, int status, LocalDateTime signedAt,String inviterName) {
        this.userType = userType;
        this.status = status;
        this.signedAt = signedAt;
        this.inviterName=inviterName;
    }

    public void setUser(User user){ // MoimMember 처음 생성할 때 User 연관관계 메서드
        this.user = user;
        user.getMoimMemberList().add(this);
    }

    public void setMoim(Moim moim){ // MoimMember 처음 생성할 때 Moim 연관관계 메서드
        this.moim = moim;
        moim.getMoimMemberList().add(this);
    }

    public void changeUserType(int userType){
        this.userType = userType;
    }
    public void changeStatus(int status){this.status = status;}

    public void changeAccount(Account account){
        this.account = account;
    }
}
