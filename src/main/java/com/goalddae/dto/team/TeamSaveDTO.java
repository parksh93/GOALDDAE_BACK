package com.goalddae.dto.team;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamSaveDTO {
        private long id;
        private String teamName;
        private String area;
        private int entryFee;
        private String preferredDay;
        private String preferredTime;
        private int averageAge;
        private String entryGender;
        private boolean recruiting;
}
