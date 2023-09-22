package com.dondoc.backend.moim.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="AllowRequest")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AllowRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestId")
    private WithdrawRequest withdrawRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moimMemberId")
    private MoimMember moimMember;

}
