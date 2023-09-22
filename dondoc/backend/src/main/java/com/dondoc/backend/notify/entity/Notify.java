package com.dondoc.backend.notify.entity;

import com.dondoc.backend.user.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="Notify")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Notify {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="title", nullable = false, length = 100)
    private String title;

    @Column(name="content", nullable = false, length = 255)
    private String content;

    @Column(name="createdAt", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    @Column(name = "notifyType",nullable = false, updatable = false)
    private int notifyType;

    public Notify(String title, String content, int notifyType) {
        this.title = title;
        this.content = content;
        this.notifyType = notifyType;
    }

    /**
     * Notify와 User가 양방향으로 바뀌면 함수 수정필요
     */
    public void setUser(User user){
        this.user = user;
    }
}
