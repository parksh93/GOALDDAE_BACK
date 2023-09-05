package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    // 홈(첫 예약한 팀)
    @Column(nullable = false)
    private long home;

    // 홈 유저
    @Column(nullable = false)
    private long homeUserId;

    // 어웨이(상대팀)
    @Column(nullable = false)
    private long away;

    // 어웨이 유저
    @Column(nullable = false)
    private long homeAwayId;

    // 인원수
    @Column(nullable = false)
    private long playerNumber;

    // 남녀 구분
    @Column(nullable = false)
    private String gender;
}
