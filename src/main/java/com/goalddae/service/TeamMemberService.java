package com.goalddae.service;

import com.goalddae.dto.team.TeamMemberCheckDTO;
import com.goalddae.dto.team.TeamMemberDTO;

import java.util.List;

public interface TeamMemberService {

    List<TeamMemberCheckDTO> findAllTeamMembersByTeamId(long teamId);
    TeamMemberCheckDTO findByUserId(long userId, long teamId);
    int findTeamManagerByUserId(long userId, long teamId);
    void addTeamMember(TeamMemberDTO teamMemberDTO);
    void removeTeamMember(TeamMemberDTO teamMemberDTO);
}
