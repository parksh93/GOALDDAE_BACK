package com.goalddae.service;

import com.goalddae.dto.team.TeamMemberDTO;

import java.util.List;

public interface TeamMemberService {

    List<TeamMemberDTO> findByTeamIdMember(long teamId);

    void addTeamMember(TeamMemberDTO teamMemberDTO);

    void deleteByUserIdMember(long usersId);
}
