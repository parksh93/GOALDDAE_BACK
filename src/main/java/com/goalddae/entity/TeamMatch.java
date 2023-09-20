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
public class TeamMatch {

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

    // 레벨
    @Column(nullable = false)
    private String level;

    // 신청 가능 상태(실제 DB적용 x)
    @Transient
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    // 홈(첫 예약한 팀)
    @Column(nullable = false)
    private long homeTeamId;

    // 홈 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="home_user_id")
    private User homeUser;

    // 어웨이(상대팀)
    @Column
    private long awayTeamId;

    // 어웨이 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="away_user_id")
    private User awayUser;

    // 외래키 형성 - 예약구장
    @ManyToOne
    @JoinColumn(name="reservation_field")
    private ReservationField reservationField;

    // 외래키 형성 - 매치요청
    @OneToMany(mappedBy = "teamMatch")
    private List<TeamMatchRequest> requests;

    // 팀 매치 신청 - 어웨이 팀장
    public void applyAway(User awayUser, long awayTeamId) {
        this.awayUser = awayUser;
        this.awayTeamId = awayTeamId;
    }
}
