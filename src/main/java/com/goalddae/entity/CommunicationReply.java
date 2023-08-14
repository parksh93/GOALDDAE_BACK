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
    private String userId;  // 작성자 (id)

    @Column(nullable = false)
    private String writer; // 작성자 (닉네임)

    @Column
    private long parentId; // 부모 댓글의 ID (일반댓글일경우 본인)

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

    @PrePersist
    @PreUpdate
    public void setNullParentId() {
        if (parentId == 0) {
            parentId = id; // Set parentId to the same value as id if it's null
        }
    }

}
