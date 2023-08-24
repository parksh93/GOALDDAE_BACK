
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
public class SoccerField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String fieldName;   // 구장명

    @Column(nullable = false)
    private int toiletStatus;   // 화장실 여부

    @Column(nullable = false)
    private int showerStatus;   //샤워실 여부

    @Column(nullable = false)
    private int parkingStatus;  // 주차장 여부

    @Column(nullable = false)
    private String fieldSize ;   // 구장 크기

    // 이미지의 경우 최소 하나 이상 최대 3개이하로 저장 가능
    @Column(nullable = false)
    private String fieldImg1;

    @Column
    private String fieldImg2;

    @Column
    private String fieldImg3;

    @Column(nullable = false)
    private int reservationFee;    // 대관비

    @Column(nullable = false)
    private String inOutWhether;    // 실내외 여부

    @Column(nullable = false)
    private String grassWhether;    // 잔디 여부

    @Column(nullable = false)
    private String region;          // 지역
}
