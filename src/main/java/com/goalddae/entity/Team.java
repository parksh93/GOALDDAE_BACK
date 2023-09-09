package com.goalddae.entity;


import com.goalddae.dto.team.TeamListDTO;
import com.goalddae.dto.team.TeamUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String teamName;    // 팀이름

    @Column(nullable = false)
    private String area;    // 지역

    @Column(nullable = false)
    private int averageAge; // 평균나이

    @Column(nullable = false)
    private boolean recruiting; // 모집중

    @Column
    private String teamIntroduce;   // 팀 소개글

    @Column(nullable = false)
    private int entryFee;  // 입단비

    @Column(nullable = false)
    private String entryGender; // 입단 가능 성별

    @Column(nullable = false)
    private LocalDateTime teamCreate;   // 팀생성일

    @Column(nullable = false)
    private String teamProfileImgUrl;  // 팀프로필 이미지

    @Column(nullable = false)
    private LocalDateTime teamProfileUpdate;    // 팀프로필 업데이트일

    @Column(nullable = false)
    private String preferredDay;    // 선호 요일

    @Column(nullable = false)
    private String preferredTime;   // 선호 시간

    public Team(Long id, String teamName, String area, int averageAge, int entryFee, String entryGender,
                String teamProfileImgUrl, String preferredDay, String preferredTime) {
        this.id = id;
        this.teamName = teamName;
        this.area = area;
        this.averageAge = averageAge;
        this.entryFee = entryFee;
        this.entryGender = entryGender;
        this.teamProfileImgUrl = teamProfileImgUrl;
        this.preferredDay = preferredDay;
        this.preferredTime = preferredTime;
    }

    public static Team toEntity(TeamListDTO teamListDTO){
        return Team.builder()
                .id(teamListDTO.getId())
                .teamName(teamListDTO.getTeamName())
                .area(teamListDTO.getArea())
                .averageAge(teamListDTO.getAverageAge())
                .recruiting(teamListDTO.isRecruiting())
                .entryGender(teamListDTO.getEntryGender())
                .teamProfileImgUrl(teamListDTO.getTeamProfileImgUrl())
                .build();
    }

    public static Team toEntity(TeamUpdateDTO teamUpdateDTO){
        return Team.builder()
                .id(teamUpdateDTO.getId())
                .teamName(teamUpdateDTO.getTeamName())
                .area(teamUpdateDTO.getArea())
                .averageAge(teamUpdateDTO.getAverageAge())
                .recruiting(teamUpdateDTO.isRecruiting())
                .entryFee(teamUpdateDTO.getEntryFee())
                .entryGender(teamUpdateDTO.getEntryGender())
                .teamProfileImgUrl(teamUpdateDTO.getTeamProfileImgUrl())
                .preferredDay(teamUpdateDTO.getPreferredDay())
                .preferredTime(teamUpdateDTO.getPreferredTime())
                .build();
    }

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