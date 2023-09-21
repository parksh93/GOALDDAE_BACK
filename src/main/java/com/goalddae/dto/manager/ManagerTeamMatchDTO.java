package com.goalddae.dto.manager;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ManagerTeamMatchDTO {
    private long id;
    private long fieldId;
    private String fieldName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long playerNumber;
    private String gender;
    private String level;
    private long userId;
}
