package com.goalddae.repository;

import com.goalddae.entity.SoccerField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoccerFieldRepository extends JpaRepository<SoccerField, Long> {
    // 검색 기능 - cityNames.json
    List<SoccerField> findByRegionContainingOrFieldNameContaining(String region, String fieldName);
    // 필터 기능 - province.json
    List<SoccerField> findByProvinceContaining(String province);
    SoccerField findByFieldName(String fieldName);
}
