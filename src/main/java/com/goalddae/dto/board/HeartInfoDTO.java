package com.goalddae.dto.board;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HeartInfoDTO {

    private long heartCount; // 총 좋아요수
    private boolean isHearted; // 유저의 좋아요여부

}
