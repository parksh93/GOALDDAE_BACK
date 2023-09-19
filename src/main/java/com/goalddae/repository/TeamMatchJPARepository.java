package com.goalddae.repository;

import com.goalddae.entity.TeamMatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TeamMatchJPARepository extends JpaRepository<TeamMatch, Long> {
    // 타임라인 - 일자, 지역, 남녀구분
    @Query("SELECT tm FROM TeamMatch tm WHERE tm.startTime BETWEEN :startTime AND :endTime AND tm.reservationField.soccerField.province = :province AND (tm.gender = :gender OR :gender IS NULL)")
    Page<TeamMatch> findMatches(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("province") String province,
            @Param("gender") String gender,
            Pageable pageable);
}