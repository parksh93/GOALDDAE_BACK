package com.goalddae.dto.manager;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ManagerTeamInfoDTO {
    private long homeTeamId;
    private String homeTeamName;
    private long awayTeamId;
    private String awayTeamName;
}
