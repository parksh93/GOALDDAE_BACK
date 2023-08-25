package com.goalddae.dto.team;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamApplyDTO {
    private long id;
    private LocalDateTime teamApplyDate;
    private int teamAcceptStatus;
    private long teamId;
}
