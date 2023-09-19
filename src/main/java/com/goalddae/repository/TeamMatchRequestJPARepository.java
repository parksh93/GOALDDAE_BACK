package com.goalddae.repository;

import com.goalddae.entity.TeamMatch;
import com.goalddae.entity.TeamMatchRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamMatchRequestJPARepository extends JpaRepository<TeamMatchRequest, Long> {
        Optional<TeamMatchRequest> findByTeamMatch(TeamMatch match);
}
