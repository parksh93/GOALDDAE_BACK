package com.goalddae.repository;

import com.goalddae.entity.SoccerField;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;

public interface SoccerFieldRepository extends JpaRepository<SoccerField, Long> {
    // 구장이름 조회
    SoccerField findByFieldName(String fieldName);
    // 검색 기능 - cityNames.json
    List<SoccerField> findByRegionContainingOrFieldNameContaining(String region, String fieldName);
    // 예약구장리스트 조회
    @Query("SELECT s FROM SoccerField s WHERE " +
            "s.region = :region AND " +
            "s.operatingHours <= :operatingHours AND s.closingTime >= :closingTime AND " +
            "s.inOutWhether = :inOutWhether AND s.grassWhether = :grassWhether")
    List<SoccerField> findAvailableField(@Param("region") String region,
                                          @Param("operatingHours") LocalTime operatingHours,
                                          @Param("closingTime") LocalTime closingTime,
                                          @Param("inOutWhether") String inOutWhether,
                                          @Param("grassWhether") String grassWhether);
}
