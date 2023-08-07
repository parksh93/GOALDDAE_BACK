package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunicationReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
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
