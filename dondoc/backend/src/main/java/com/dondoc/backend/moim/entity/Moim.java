package com.dondoc.backend.moim.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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

}
