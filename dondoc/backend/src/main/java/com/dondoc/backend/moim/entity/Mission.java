package com.dondoc.backend.moim.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="Mission")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="moimMember")
    private MoimMember moimMember;

    @Column(name="title", nullable = false, columnDefinition = "LONGTEXT")
    private String title;

    @Column(name="amount", nullable = false)
    private int amount;

    @Column(name="content", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Column(name="status", nullable = false)
    private int status;

    @Column(name="createdAt", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name="startDate")
    private LocalDateTime startDate;

    @Column(name="endDate", updatable = false)
    private LocalDateTime endDate;

}
