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
public class IndividualMatchRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    // 개인매치 ID
    @Column(nullable = false)
    private long IndividualMatchId;

    // 매치 신청한 유저
    @Column(nullable = false)
    private long userId;

    // 매치 신청한 일정
    @Column(nullable = false)
    private LocalDateTime requestDate;
}
