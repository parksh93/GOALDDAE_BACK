package com.goalddae.dto.team;

import com.goalddae.dto.user.GetUserInfoDTO;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamAcceptApplyDTO {
    private TeamApplyDTO teamApplyDTO;
    private TeamMemberDTO teamMemberDTO;
    private GetUserInfoDTO getUserInfoDTO;

}
