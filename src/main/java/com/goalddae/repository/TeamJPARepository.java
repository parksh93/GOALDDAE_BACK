package com.goalddae.repository;

import com.goalddae.dto.team.TeamListDTO;
import com.goalddae.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamJPARepository extends JpaRepository<Team, Long> {

    List<Team> findAll();
    Team findTeamById(Long id);
    void deleteTeamById(Long id);
    List<Team> findByTeamName(String searchTerm);
    List<Team> findByArea(String area);
    List<Team> findByRecruiting(boolean recruiting);
    List<Team> findByAreaAndRecruiting(String area ,boolean recruiting);
    Team findFirstByOrderByIdDesc();
}
