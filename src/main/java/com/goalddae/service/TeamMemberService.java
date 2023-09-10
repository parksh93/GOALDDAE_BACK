package com.goalddae.service;

import com.goalddae.dto.team.TeamMemberDTO;
import com.goalddae.dto.team.TeamMemberListDTO;

import java.util.List;

public interface TeamMemberService {

    List<TeamMemberListDTO> findAllTeamMember();

    TeamMemberDTO findById(long id);

    void addTeamMember(TeamMemberDTO teamMemberDTO);

    void deleteTeamMemberById(long id);
}
