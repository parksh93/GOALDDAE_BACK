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
            "s.province = :province AND " +
            "s.inOutWhether = :inOutWhether AND s.grassWhether = :grassWhether")
    List<SoccerField> findAvailableField(@Param("province") String province,
                                         @Param("inOutWhether") String inOutWhether,
                                         @Param("grassWhether") String grassWhether);
    List<SoccerField> findAllByProvince(String province);
    List<SoccerField> findByProvinceAndGrassWhether(String province, String grassWhether);
    List<SoccerField> findByProvinceAndInOutWhether(String province, String inOutWhether);
    List<SoccerField> findByProvinceAndInOutWhetherAndGrassWhether(String province, String inOutWhether, String grassRather);
}
