package com.goalddae.dto.team;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberDTO {
    private long id;
    private LocalDateTime userJoinDate;
    private int teamManager;    // 0: 팀장   1: 팀원
    private long teamId;
    private long userId;
}
