package com.goalddae.dto.team;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberListDTO {
    private long id;
    private String nickname;
    private int teamManager;
    private long teamId;
    private long userId;
}
