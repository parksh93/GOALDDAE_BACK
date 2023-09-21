package com.goalddae.dto.manager;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ManagerTeamMatchResultDTO {

    private Long matchId;
    private Long homeTeamId;
    private Long awayTeamId;
    private Long homeScore;
    private Long awayScore;

}
