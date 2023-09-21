package com.goalddae.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

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

    // 레벨
    @Column(nullable = false)
    private String level;

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
    @JoinColumn(name="reservation_field")
    private ReservationField reservationField;

    // 외래키 형성 - 매치요청
    @OneToMany(mappedBy = "individualMatch")
    private List<IndividualMatchRequest> requests;

    // 매니저 id
    @Column
    private long managerId;

    public void setManagerId(long managerId){
        this.managerId = managerId;
    }

}