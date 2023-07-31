package com.goalddae.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String teamName;    // 팀이름

    @Column(nullable = false)
    private String area;    // 지역

    @Column(nullable = false)
    private int averageAge; // 나이대

    @Column(nullable = false)
    private int entryFree;  // 입단비

    @Column(nullable = false)
    private String entryGender; // 입단 가능 성별

    @Column(nullable = false)
    private LocalDateTime teamCreate;   // 팀생성일

    @Column(nullable = false)
    private String teamProfileImgUrl;  // 팀프로필

    @Column(nullable = false)
    private LocalDateTime teamProfileUpdate;    // 팀프로필 업데이트일

    @Column(nullable = false)
    private String preferredDay;    // 선호 요일

    @Column(nullable = false)
    private String preferredTime;   // 선호 시간

    @PrePersist
    public void setInformation() {
        this.teamCreate = LocalDateTime.now();
        this.teamProfileUpdate = LocalDateTime.now();
        this.teamProfileImgUrl = "기본 프로필 주소 필요";
    }

    @PreUpdate
    public void setUpdateTime() {
        this.teamProfileUpdate = LocalDateTime.now();
    }
}