package com.goalddae.repository;

import com.goalddae.entity.IndividualMatch;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface IndividualMatchJPARepository extends JpaRepository<IndividualMatch, Long> {

    // 타임라인 - 일자, 지역, 레벨, 남녀구분
    @Query("SELECT im FROM IndividualMatch im WHERE im.startTime BETWEEN :startTime AND :endTime AND im.reservationField.soccerField.province = :province AND (im.level = :level OR :level IS NULL) AND (im.gender = :gender OR :gender IS NULL)")
    List<IndividualMatch> findMatches(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("province") String province,
            @Param("level") String level,
            @Param("gender") String gender);

    // 특정 유저의 신청한 매치들을 조회할때 사용
    List<IndividualMatch> findByUserId(long userId);
}
