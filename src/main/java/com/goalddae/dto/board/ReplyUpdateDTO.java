package com.goalddae.dto.board;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyUpdateDTO {
    private long id;
    private String content;   // 내용
}
