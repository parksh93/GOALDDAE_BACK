package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsedTransactionReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    @Column(nullable = false)
    private long boardId;

    @Column(nullable = false)
    private String userId;  // 작성자 (id)

    @Column(nullable = false)
    private String writer; // 작성자 (닉네임)

    @Column(nullable = false)
    private LocalDateTime replyWriteDate;

    @Column(nullable = false)
    private LocalDateTime replyUpdateDate;

    @PrePersist
    public void setTime() {
        this.replyWriteDate = LocalDateTime.now();
        this.replyUpdateDate = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdateTime() {
        this.replyUpdateDate = LocalDateTime.now();
    }
}
