package com.goalddae.dto.team;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamListDTO {
    private Long id;
    private String teamName;
    private String area;
    private boolean recruiting;
    private int averageAge;
    private String entryGender;
    private String teamProfileImgUrl;
}
