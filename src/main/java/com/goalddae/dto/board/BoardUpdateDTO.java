package com.goalddae.dto.board;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateDTO {
    private long id;
    private String title;   // 제목
    private String content;   // 내용
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
}
