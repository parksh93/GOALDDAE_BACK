package com.goalddae.service;

import com.goalddae.dto.team.TeamListDTO;
import com.goalddae.entity.Team;

import java.util.List;

public interface TeamService {

    List<TeamListDTO> findAll();

    Team findTeamById(Long id);

    void save(Team team);

    void update(Team team);

    void deleteTeamById(Long id);

    List<Team> findByTeamName(String searchTerm);

    List<TeamListDTO> findByArea(String area);

    List<TeamListDTO> findByRecruiting(boolean recruiting);

    List<TeamListDTO> findByAreaAndRecruiting(String area, boolean recruiting);
}
