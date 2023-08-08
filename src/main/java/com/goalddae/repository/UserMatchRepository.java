package com.goalddae.repository;

import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.Match;
import com.goalddae.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMatchRepository extends JpaRepository<Match, Long> {
    List<Match> findMatchById(long userId);

}
