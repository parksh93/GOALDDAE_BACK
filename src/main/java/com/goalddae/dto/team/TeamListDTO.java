package com.goalddae.dto.team;

import com.goalddae.entity.Team;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamListDTO {
    private Long id;
    private String teamName;
    private String area;
    private int averageAge;
    private boolean recruiting;
    private String entryGender;
    private String teamProfileImgUrl;


    public static TeamListDTO toDTO(Team team){
        return TeamListDTO.builder()
                .id(team.getId())
                .teamName(team.getTeamName())
                .area(team.getArea())
                .averageAge(team.getAverageAge())
                .recruiting(team.isRecruiting())
                .entryGender(team.getEntryGender())
                .teamProfileImgUrl(team.getTeamProfileImgUrl())
                .build();
    }

    public TeamListDTO(Team team){
        this.id = team.getId();
        this.teamName = team.getTeamName();
        this.area = team.getArea();
        this.averageAge = team.getAverageAge();
        this.recruiting = team.isRecruiting();
        this.entryGender = team.getEntryGender();
        this.teamProfileImgUrl = getTeamProfileImgUrl();
    }

}


