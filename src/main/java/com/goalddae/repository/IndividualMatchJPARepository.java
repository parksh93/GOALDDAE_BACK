package com.goalddae.repository;

import com.goalddae.entity.IndividualMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IndividualMatchJPARepository extends JpaRepository<IndividualMatch, Long> {

    // 매치 시작 시간 조회
    List<IndividualMatch> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
    // 특정 유저의 신청한 매치들을 조회할때 사용
    List<IndividualMatch> findByUserId(long userId);
}
