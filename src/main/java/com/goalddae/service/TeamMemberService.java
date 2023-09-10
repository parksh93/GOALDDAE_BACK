package com.goalddae.service;

import com.goalddae.dto.team.TeamMemberDTO;

import java.util.List;

public interface TeamMemberService {

    List<TeamMemberDTO> findAll();

    TeamMemberDTO findById(long id);

    void addTeamMember(TeamMemberDTO teamMemberDTO);

    void deleteTeamMemberById(long id);
}
