package com.goalddae.dto.board;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class MyBoardListDTO {

    private long id;
    private long userId;
    private String writer;
    private String title;
    private String content;
    private int replyCount;
    private int heartCount;
    private long count;
    private LocalDateTime writeDate;

}
