package com.goalddae.dto.board;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardListDTO {

    private long id;
    private String writer;  // 작성자
    private String title;   // 제목
    private LocalDateTime writeDate;    // 작성일
    private LocalDateTime updateDate;   // 수정일
    private String img1;
    private int boardSortation;    // 게시판 구분
}
