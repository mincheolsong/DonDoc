package com.dondoc.backend.group.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    @JoinColumn(name = "GroupMemberId")
    private GroupMember groupMember;

}
