package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private long userId;  // 작성자 (유저id)

    @Column(nullable = false)
    private String writer; // 작성자 (닉네임)

    @Column
    private long parentId; // 부모 댓글의 ID (일반댓글일경우 null)

    @Column(nullable = false)
    private String content; // 본문

    @Column(nullable = false)
    private LocalDateTime replyWriteDate;

    @Column(nullable = false)
    private LocalDateTime replyUpdateDate;

    @Column(nullable = false)
    private int status; // 0 : 정상, 1 : 본인 삭제, 2 : 관리자 삭제


    @PrePersist
    public void setTime() {
        this.replyWriteDate = LocalDateTime.now();
        this.replyUpdateDate = LocalDateTime.now();
    }

    public void deleteByUser() {
        this.status = 1;
    }

    public void deleteByAdmin() {
        this.status = 2;
    }

}
