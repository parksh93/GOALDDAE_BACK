package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    // 홈 유저(홈팀 대표)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="home_user_id")
    private User homeUser;

    // 홈 팀 선수들 (홈 팀 대표 포함)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "team_match_home_players",
            joinColumns = @JoinColumn(name = "team_match_id"),
            inverseJoinColumns = @JoinColumn(name ="user_id"))
    @Builder.Default
    private List<User> homePlayers = new ArrayList<>();

    // 어웨이(상대팀)
    @Column
    private long awayTeamId;

    // 어웨이 유저(어웨이팀 대표)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="away_user_id")
    private User awayUser;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "team_match_away_players",
            joinColumns = @JoinColumn(name ="team_match_id"),
            inverseJoinColumns =@JoinColumn(name ="user_id"))
    @Builder.Default
    private List<User> awayPlayers= new ArrayList<>();

    // 외래키 형성 - 예약구장
    @ManyToOne
    @JoinColumn(name="reservation_field")
    private ReservationField reservationField;

    // 매니저 id
    @Column
    private long managerId;

    public void setManagerId(long managerId){
        this.managerId = managerId;
    }

    // 홈팀과 어웨이팀이 같은지 확인하기 위함
    public boolean isSameTeam(Long awayTeamId) {
        return this.homeTeamId == awayTeamId;
    }

    public void applyAway(User awayUser, long awayTeamId) {
        this.awayUser = awayUser;
        this.awayTeamId = awayTeamId;
    }

    // 양방향 관계 - TeamMatchRequest 엔터티의 리스트를 반환하도록 설정
    @OneToMany(mappedBy = "teamMatch", fetch = FetchType.LAZY)
    private List<TeamMatchRequest> requests;

    public List<TeamMatchRequest> getRequests() {
        if (requests == null) {
            requests = new ArrayList<>();
        }
        return requests;
    }

    public boolean hasApplied(Long userId) {
        return awayUser != null && awayUser.getId() == userId;
    }

    public void cancelApplication(Long userId) {
        if (awayUser == null || awayUser.getId() != userId) {
            throw new RuntimeException("이미 신청하지 않은 매치입니다.");
        }
        this.awayUser = null;
        this.awayTeamId = 0L;
    }
}