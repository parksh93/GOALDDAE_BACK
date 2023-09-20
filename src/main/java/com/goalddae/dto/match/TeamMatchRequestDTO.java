package com.goalddae.dto.match;

import lombok.*;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamMatchRequestDTO {
    private long id;
    private long userId;
    private long matchId;
    private long awayTeamId;
}
