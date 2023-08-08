package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name="match")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private long matchId;

    @Column(nullable = false)
    private boolean like;       // 좋아요

    @Column(nullable = false)
    private long views;         // 조회수

    @Column(nullable = false)
    private int gender;         // 성별

    @Column(nullable = false)
    private int minimum;        // 최소인원

    @Column(nullable = false)
    private int maximum;        // 최대인원

    @Column(nullable = false)
    private int matchPeople;    // 경기인원

    @Column(nullable = false)
    private LocalDateTime recruitStart;     // 모집시작

    @Column(nullable = false)
    private LocalDateTime recruitEnd;       // 모집종료

    @Column(nullable = false)
    private long managerId;                 // 매니저 ID

    @Column(nullable = false)
    private long soccerfieldId;              // 구장 ID

    @Column(nullable = false)
    private long fieldReservationId;         // 구장예약 ID

    @Column(nullable = false)
    private long userId;                     // 유저 ID
}
