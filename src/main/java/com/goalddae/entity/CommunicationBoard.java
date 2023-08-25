package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CommunicationBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    @Column(nullable = false)
    private String userId;  // 작성자 (id)

    @Column(nullable = false)
    private String writer; // 작성자 (닉네임)

    @Column(nullable = false)
    private String title;   // 제목

    @Column(nullable = false)
    private String content; // 본문

    @Column(nullable = false)
    private LocalDateTime writeDate;    // 작성일

    @Column(nullable = false)
    private LocalDateTime updateDate;   // 수정일

    @Column
    private String img1;

    @Column
    private String img2;

    @Column
    private String img3;

    @Column
    private String img4;

    @Column
    private String img5;

    @Column(nullable = false)
    private int boardSortation;    // 게시판 구분

    @Column(nullable = false)
    private long count; // 조회수

    @PrePersist
    public void setTime() {
        this.writeDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    public void viewCountUp() {
        this.count++;
    }


}
