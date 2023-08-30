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
public class IndividualMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    // 매치 시작 일자와 시간
    @Column(nullable = false)
    private LocalDateTime startTime;

    // 매치 종료 일자와 시간
    @Column(nullable = false)
    private LocalDateTime endTime;

    // 매치 유저수
    @Column(nullable = false)
    private long playerNumber;

    // 남녀 구분
    @Column(nullable = false)
    private String gender;

    // 신청 가능 상태(실제 DB적용 x)
    @Transient
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    // 외래키 형성 - 매치할 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    // 외래키 형성 - 예약구장
    @ManyToOne
    @JoinColumn(name="soccer_field_id")
    private ReservationField reservationField;

    // 외래키 형성 - 매치요청
    @OneToMany(mappedBy = "individualMatch")
    private List<IndividualMatchRequest> requests;
}