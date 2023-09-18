package com.goalddae.service;

import com.goalddae.dto.team.TeamMemberCheckDTO;
import com.goalddae.dto.team.TeamMemberDTO;
import com.goalddae.dto.user.GetUserInfoDTO;

import java.util.List;

public interface TeamMemberService {

    List<TeamMemberCheckDTO> findAllTeamMembersByTeamId(long teamId);
    TeamMemberCheckDTO findByUserId(long userId, long teamId);
    int findTeamManagerByUserId(long userId, long teamId);
    void addTeamMember(TeamMemberDTO teamMemberDTO);
    void removeTeamMember(long usersId, long teamId, GetUserInfoDTO getUserInfoDTO);
}
