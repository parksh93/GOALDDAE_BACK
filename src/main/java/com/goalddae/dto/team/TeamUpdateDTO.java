package com.goalddae.dto.team;

import com.goalddae.entity.Team;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamUpdateDTO {

    private Long id;
    private String teamName;    // 팀이름
    private String area;    // 지역
    private int averageAge; // 평균나이
    private boolean recruiting; // 모집중
    private String teamIntroduce;   // 팀 소개글
    private int entryFee;  // 입단비
    private String entryGender; // 입단 가능 성별
    private String teamProfileImgUrl;  // 팀프로필 이미지
    private LocalDateTime teamProfileUpdate;    // 팀프로필 업데이트일
    private String preferredDay;    // 선호 요일
    private String preferredTime;   // 선호 시간

    public static TeamUpdateDTO toDTO(Team team){
        return TeamUpdateDTO.builder()
                .id(team.getId())
                .teamName(team.getTeamName())
                .area(team.getArea())
                .averageAge(team.getAverageAge())
                .recruiting(team.isRecruiting())
                .teamIntroduce(team.getTeamIntroduce())
                .entryFee(team.getEntryFee())
                .entryGender(team.getEntryGender())
                .teamProfileImgUrl(team.getTeamProfileImgUrl())
                .teamProfileUpdate(team.getTeamProfileUpdate())
                .preferredDay(team.getPreferredDay())
                .preferredTime(team.getPreferredTime())
                .build();
    }

    public Team toEntity() {
        return Team.builder()
                .id(id)
                .teamName(teamName)
                .area(area)
                .averageAge(averageAge)
                .recruiting(recruiting)
                .teamIntroduce(teamIntroduce)
                .entryFee(entryFee)
                .entryGender(entryGender)
                .teamProfileImgUrl(teamProfileImgUrl)
                .teamProfileUpdate(teamProfileUpdate)
                .preferredDay(preferredDay)
                .preferredTime(preferredTime)
                .build();
    }


    public TeamUpdateDTO(Team team){
        this.id = team.getId();
        this.teamName = team.getTeamName();
        this.area = team.getArea();
        this.averageAge = team.getAverageAge();
        this.recruiting = team.isRecruiting();
        this.teamIntroduce = team.getTeamIntroduce();
        this.entryFee = team.getEntryFee();
        this.entryGender = team.getEntryGender();
        this.teamProfileImgUrl = getTeamProfileImgUrl();
        this.teamProfileUpdate = getTeamProfileUpdate();
        this.preferredDay = team.getPreferredDay();
        this.preferredTime = team.getPreferredTime();
    }

}