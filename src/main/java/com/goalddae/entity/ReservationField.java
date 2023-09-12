package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    // 예약할 구장
    @ManyToOne
    @JoinColumn(name="soccer_field_id")
    private SoccerField soccerField;

    // 예약한 날짜
    @Column(nullable = false)
    private LocalDateTime reservedDate;

    // 예약 시작 일정
    @Column(nullable = false)
    private LocalDateTime startDate;

    // 예약 종료 일정
    @Column(nullable = false)
    private LocalDateTime endDate;

    // 외래 키 형성 - 예약한 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;


    // 외래키 형성 - 개인매치
    @OneToMany(mappedBy = "reservationField")
    private List<IndividualMatch> individualMatches;

    }

