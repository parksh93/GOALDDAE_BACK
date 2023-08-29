package com.goalddae.repository;

import com.goalddae.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJPARepository extends JpaRepository<Team, Long> {
}
