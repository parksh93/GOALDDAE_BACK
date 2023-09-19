package com.goalddae.dto.fieldReservation;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FieldReservationDTO {
    private Long soccerFieldId; // 구장 ID
    private String reservedDate; // 8자리 문자열, 예: "20230901"
    private int startTime;       // 0~23 사이의 int, 예: 14
    private int totalHours;      // 총 대관 시간, 예: 2
    private Long userId; // 유저 ID
    private Long playerNumber; // 매치 유저 수
    private String gender; // 남녀 구분
    private String level; // 레벨
    private Long teamId; // 팀 id (팀매치가 아닐경우엔 -1)
}
