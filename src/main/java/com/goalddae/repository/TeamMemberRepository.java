package com.goalddae.repository;

import com.goalddae.dto.team.TeamMemberDTO;
import com.goalddae.entity.Team;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeamMemberRepository {
    void createTeamMemberTable(@Param("teamId") Long teamId);

    List<TeamMemberDTO> findAll();

    TeamMemberDTO findById(long id);

    void addTeamMember(TeamMemberDTO teamMemberDTO);

    void deleteTeamMemberById(long id);

}
