package com.goalddae.dto.match;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamMatchDTO {
    private long id;
    private long fieldId;
    private LocalDateTime startTime;
    private String fieldName;
    private String status;
    private long playerNumber;
    private String gender;
}