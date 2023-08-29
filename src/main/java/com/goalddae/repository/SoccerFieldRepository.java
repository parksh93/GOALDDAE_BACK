package com.goalddae.repository;

import com.goalddae.entity.SoccerField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoccerFieldRepository extends JpaRepository<SoccerField, Long> {
    List<SoccerField> findByRegionContainingOrFieldNameContaining(String region, String fieldName);
    SoccerField findByFieldName(String fieldName);
}
