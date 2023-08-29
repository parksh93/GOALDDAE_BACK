package com.goalddae.repository;

import com.goalddae.entity.TeamMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMatchJPARepository extends JpaRepository<TeamMatch, Long> {
}