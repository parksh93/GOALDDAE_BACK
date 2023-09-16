package com.goalddae.repository;

import com.goalddae.entity.SoccerField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoccerFieldRepository extends JpaRepository<SoccerField, Long> {
    // 구장이름 조회
    SoccerField findByFieldName(String fieldName);
    // 검색 기능 - cityNames.json
    List<SoccerField> findByRegionContainingOrFieldNameContaining(String region, String fieldName);
    // 예약할 구장 조회
    Page<SoccerField> findAllByProvince(String province, Pageable pageable);
    Page<SoccerField> findByProvinceAndGrassWhether(String province, String grassWhether, Pageable pageable);
    Page<SoccerField> findByProvinceAndInOutWhether(String province, String inOutWhether, Pageable pageable);
    Page<SoccerField> findByProvinceAndInOutWhetherAndGrassWhether(String province,
                                                                   String inOutWhether,
                                                                   String grassRather,
                                                                   Pageable pageable);
}