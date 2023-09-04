package com.goalddae.repository;

import com.goalddae.entity.IndividualMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface IndividualMatchJPARepository extends JpaRepository<IndividualMatch, Long> {

    // 타임라인 조회
    List<IndividualMatch> findByStartTimeBetweenAndReservationField_SoccerField_Province(LocalDateTime startTime, LocalDateTime endTime, String province);

    // 특정 유저의 신청한 매치들을 조회할때 사용
    List<IndividualMatch> findByUserId(long userId);
}
