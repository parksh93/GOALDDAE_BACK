package com.goalddae.dto.team;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberCheckDTO {

    private long userId;
    private String name;
    private String profileImgUrl;
    private String preferredCity;
    private String preferredArea;
    private long teamId;
    private int teamAcceptStatus;   // 0: 가입신청중(대기) 1: 가입수락 2: 가입거절
    private int teamManager; // 0: 팀장 1: 팀원
}
