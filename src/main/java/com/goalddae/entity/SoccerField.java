
package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private LocalTime operatingHours; // 운영 시작 시간

    @Column(nullable = false)
    private LocalTime closingTime; // 운영 종료 시간

    @Column(nullable = false)
    private String region;          // 지역

    @Column(nullable = false)
    private int reservationFee;    // 대관비

    @Column(nullable = false)
    private String fieldSize;   // 구장 크기

    @Column(nullable = false)
    private String inOutWhether;    // 실내외 여부

    @Column(nullable = false)
    private String grassWhether;    // 잔디 종류

    @Column(nullable = false)
    private boolean toiletStatus;   // 화장실 여부

    @Column(nullable = false)
    private boolean showerStatus;   //샤워실 여부

    @Column(nullable = false)
    private boolean parkingStatus;  // 주차장 여부

    @Column(nullable = false)
    private String province;        // 선호 도시 - 서울, 경기, 인천 .....

    @Column(nullable = false)
    private String region;        // 선호 지역 - 강남구, 강동구 .......

    @Column(nullable = false)
    private String address; // 상세 주소

    // 이미지의 경우 최소 하나 이상 최대 3개이하로 저장 가능
    @Column(nullable = false)
    private String fieldImg1;

    @Column
    private String fieldImg2;

    @Column
    private String fieldImg3;

    @Column(length = 5000)
    private String content;

    // 구장 수정을 위한 캡슐화

    public void changeFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void changeToiletStatus(boolean toiletStatus) {
        this.toiletStatus = toiletStatus;
    }

    public void changeShowerStatus(boolean showerStatus) {
        this.showerStatus = showerStatus;
    }

    public void changeParkingStatus(boolean parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    public void changeFieldSize(String fieldSize) {
        this.fieldSize = fieldSize;
    }

    public void changeImages(String fieldImg1, String fieldImg2, String fieldImg3) {
        this.fieldImg1 = fieldImg1;
        this.fieldImg2 = fieldImg2;
        this.fieldImg3 = fieldImg3;
    }

    public void changeReservationFee(int reservationFee) {
        this.reservationFee = reservationFee;
    }

    public void changeInOutWhether(String inOutWhether){
        this.inOutWhether=inOutWhether;
    }

    public void changeGrassWhether(String grassWhether){
        this.grassWhether=grassWhether;
    }

    public void changeRegion(String region){
        this.region=region;
    }

    public void changeOperatingHours(LocalTime operatingHours) {
        this.operatingHours = operatingHours;
    }

    public void changeClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public void changeProvince(String province) {
        this.province = province;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    }

