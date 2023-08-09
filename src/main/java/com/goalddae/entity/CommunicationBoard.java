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
public class CommunicationBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    @Column(nullable = false)
    private String userId;  // 작성자 (id)

    @Column(nullable = false)
    private String writer;  // 작성자 (닉네임)

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

    @PrePersist
    public void setTime() {
        this.writeDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    public void update(CommunicationBoard updatedBoard) {
        this.writer = updatedBoard.getWriter();
        this.title = updatedBoard.getTitle();
        this.content = updatedBoard.getContent();
        this.img1 = updatedBoard.getImg1();
        this.img2 = updatedBoard.getImg2();
        this.img3 = updatedBoard.getImg3();
        this.img4 = updatedBoard.getImg4();
        this.img5 = updatedBoard.getImg5();
        this.boardSortation = updatedBoard.getBoardSortation();
        this.updateDate = LocalDateTime.now();
    }

}
