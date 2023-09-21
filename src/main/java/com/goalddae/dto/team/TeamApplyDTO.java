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
    private int teamAcceptStatus;   // 0: 가입신청중(대기) 1: 가입수락 2: 가입거절
    private long teamId;
    private long userId;
}