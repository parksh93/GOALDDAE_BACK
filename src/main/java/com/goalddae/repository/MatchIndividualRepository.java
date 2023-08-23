package com.goalddae.repository;


import com.goalddae.dto.match.MatchIndividualDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MatchIndividualRepository {
    void createMatchIndividualTable(String matchIndividualId);
    void dropMatchIndividualTable(String matchIndividualId);

    // 외래키 추가
    void addForeignKeyConstraintToMatchIndividual(Long fieldId);

}