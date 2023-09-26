package com.dondoc.backend.moim.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Moim")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Moim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="identificationNumber", nullable = false, length = 255)
    private String identificationNumber;

    @Column(name="moimName", nullable = false, length = 20)
    private String moimName;

    @Column(name="introduce", nullable = false, columnDefinition = "LONGTEXT")
    private String introduce;

    /**
     * 모임계좌의 accountId (은행 Account테이블)
     */
    @JsonIgnore
    @Column(name="moimAccountId", nullable = false)
    private Long moimAccountId;

    @Column(name="moimAccountNumber", nullable = false, length = 50)
    private String moimAccountNumber;

    @Column(name="limited", nullable = false)
    private int limited;

    @Column(name="moimType", nullable = false)
    private int moimType;

    @Column(name="createdAt", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "moim")
    private List<MoimMember> moimMemberList = new ArrayList<>();

    public Moim(String identificationNumber, String moimName, String introduce, Long moimAccountId, String moimAccountNumber, int limited, int moimType) {
        this.identificationNumber = identificationNumber;
        this.moimName = moimName;
        this.introduce = introduce;
        this.moimAccountId = moimAccountId;
        this.moimAccountNumber = moimAccountNumber;
        this.limited = limited;
        this.moimType = moimType;
    }

    public Moim(String identificationNumber, String moimName, String introduce, String moimAccountNumber, int limited, int moimType) {
        this.identificationNumber = identificationNumber;
        this.moimName = moimName;
        this.introduce = introduce;
        this.moimAccountNumber = moimAccountNumber;
        this.limited = limited;
        this.moimType = moimType;
    }
}
