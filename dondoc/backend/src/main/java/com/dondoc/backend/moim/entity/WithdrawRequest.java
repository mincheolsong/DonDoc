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
@Table(name="WithdrawRequest")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class WithdrawRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="moimMemberId")
    private MoimMember moimMember;

    @Column(name="title", nullable = false, columnDefinition = "LONGTEXT")
    private String title;

    @OneToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @Column(name="amount", nullable = false)
    private int amount;

    @Column(name="content", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Column(name="status" , nullable = false)
    private int status;

    @OneToMany(mappedBy = "withdrawRequest")
    private List<AllowRequest> allowRequest = new ArrayList<>();

    @Column(name="createdAt", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

}
