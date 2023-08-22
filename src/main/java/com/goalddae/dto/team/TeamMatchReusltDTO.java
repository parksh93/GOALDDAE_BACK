package com.goalddae.dto.team;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamMatchReusltDTO {
    private long id;
    private String teamWin;
    private String teamLose;
    private int winScore;
    private int loseScore;
    private String draw;
    private long teamMatchId;
}
