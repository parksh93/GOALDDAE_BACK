package com.goalddae.service;

import com.goalddae.dto.team.TeamMemberDTO;

import java.util.List;

public interface TeamMemberService {

    List<TeamMemberDTO> findAllTeamMembersByTeamId(long teamId);
    int findTeamManagerByUserId(long userId, long teamId);

    void addTeamMember(TeamMemberDTO teamMemberDTO);

    void deleteMemberByUserId(long usersId);
}
