package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMatchRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    // 팀 매치 ID
    @Column(nullable = false)
    private long teamMatchId;

    // 매치 신청한 팀
    @Column(nullable = false)
    private long teamId;

    // 매치 신청한 일정
    @Column(nullable = false)
    private LocalDateTime requestDate;
}
