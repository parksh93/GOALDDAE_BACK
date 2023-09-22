package com.goalddae.dto.match;

import lombok.*;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamMatchRequestDTO {
    private Long awayUserId;
    private Long awayTeamId;
}
