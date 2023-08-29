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
public class ReservationField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    // 예약 구장
    @Column(nullable = false)
    private long soccerField;

    // 예약 가능 일자
    @Column(nullable = false)
    private LocalDateTime reservationDate;

    // 예약한 날짜
    @Column(nullable = false)
    private LocalDateTime reservedDate;

    // 예약 가능 시간
    @Column(nullable = false)
    private long reservedTime;

    // 예약한 유저
    @Column(nullable = false)
    private String userId;
}
