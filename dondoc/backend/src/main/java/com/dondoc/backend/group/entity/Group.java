package com.dondoc.backend.group.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="Group")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="identificationNumber", nullable = false, length = 255)
    private String identificationNumber;

    @Column(name="groupName", nullable = false, length = 20)
    private String groupName;

    @Column(name="introduce", nullable = false, columnDefinition = "LONGTEXT")
    private String introduce;

    @Column(name="groupAccount", nullable = false, length = 50)
    private String groupAccount;

    @Column(name="limit", nullable = false)
    private int limit;

    @Column(name="groupType", nullable = false)
    private int groupType;

    @Column(name="createdAt", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

}
