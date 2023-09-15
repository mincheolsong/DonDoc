package com.dondoc.backend.moim.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(name="moimAccount", nullable = false, length = 50)
    private String moimAccount;

    @Column(name="limited", nullable = false)
    private int limited;

    @Column(name="moimType", nullable = false)
    private int moimType;

    @Column(name="createdAt", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "moim")
    private List<MoimMember> moimMemberList;
    public Moim(String identificationNumber, String moimName, String introduce, String moimAccount, int limited, int moimType) {
        this.identificationNumber = identificationNumber;
        this.moimName = moimName;
        this.introduce = introduce;
        this.moimAccount = moimAccount;
        this.limited = limited;
        this.moimType = moimType;
    }
}
