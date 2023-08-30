package com.goalddae.dto.match;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IndividualMatchDTO {
    private long id;
    private long fieldId;
    private LocalDateTime startTime;
    private String fieldName;
    private String status;
    private long playerNumber;
    private String gender;

    // 플레이어수
    public String getPlayerFormat() {
        int teamSize = (int) playerNumber / 2;
        return teamSize + "대" + teamSize;
    }
}
