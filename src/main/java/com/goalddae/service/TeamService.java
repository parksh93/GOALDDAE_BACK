package com.goalddae.service;

import com.goalddae.dto.team.TeamListDTO;
import com.goalddae.dto.team.TeamUpdateDTO;
import com.goalddae.entity.Team;

import com.goalddae.dto.team.TeamSaveDTO;
import com.goalddae.entity.Team;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamService {

    List<TeamListDTO> findAll();

    Team findTeamById(Long id);

    void save(Team team);

    void update(TeamUpdateDTO teamUpdateDTO);

    void deleteTeamById(Long id);

    List<Team> findByTeamName(String searchTerm);

    List<TeamListDTO> findByArea(String area);

    List<TeamListDTO> findByRecruiting(boolean recruiting);

    List<TeamListDTO> findByAreaAndRecruiting(String area, boolean recruiting);

    boolean createTeamMemberTable(@Param("teamId") Long teamId);
    boolean createTeamApplyTable(@Param("teamId") Long teamId);
    boolean createTeamMatchResult(@Param("teamId") Long teamId);
    void save(TeamSaveDTO teamSaveDTO);
  
}
