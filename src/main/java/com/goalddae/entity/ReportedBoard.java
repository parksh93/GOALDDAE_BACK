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
public class ReportedBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private Long boardId;

    @Column(nullable = false)
    private Long reporterUserId; // 신고자 유저 id

    @Column(nullable = false)
    private Long reportedUserId; // 신고당한 유저 id

    @Column(nullable = false)
    private LocalDateTime reportedDate;

    @Column
    private String reason;

    @PrePersist
    public void setTime() {
        this.reportedDate = LocalDateTime.now();
    }

}
