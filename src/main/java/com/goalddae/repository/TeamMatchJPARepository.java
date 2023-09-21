package com.goalddae.repository;

import com.goalddae.entity.TeamMatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TeamMatchJPARepository extends JpaRepository<TeamMatch, Long> {
    // 타임라인 - 일자, 지역, 남녀구분
    @Query("SELECT tm FROM TeamMatch tm WHERE tm.startTime BETWEEN :startTime AND :endTime AND tm.reservationField.soccerField.province = :province AND (tm.gender = :gender OR :gender IS NULL)")
    Page<TeamMatch> findMatches(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("province") String province,
            @Param("gender") String gender,
            Pageable pageable);
    List<TeamMatch> findByManagerIdAndStartTimeBeforeOrderByStartTimeDesc(Long managerId, LocalDateTime currentTime);
    // 매치를 생성하거나 요청한 유저를 확인
    @Query("SELECT tm FROM TeamMatch tm WHERE tm.id = :matchId AND tm.homeUser IS NOT NULL AND tm.awayUser IS NOT NULL")
    Optional<TeamMatch> findValidMatchById(@Param("matchId") Long matchId);
    // 팀 매치 상세페이지에서 정보 조회
    @Query("SELECT tm FROM TeamMatch tm JOIN FETCH tm.reservationField rf JOIN FETCH rf.soccerField sf WHERE tm.id = :matchId")
    Optional<TeamMatch> findWithSoccerFieldById(@Param("matchId") Long matchId);
}