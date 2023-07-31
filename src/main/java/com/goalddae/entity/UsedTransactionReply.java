package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class UsedTransactionReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long boardId;

    @Column(nullable = false)
    private String writer;

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
