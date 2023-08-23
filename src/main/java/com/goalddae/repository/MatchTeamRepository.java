package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MatchTeamRepository {
    void createMatchTeamTable(String matchTeamId);
    void dropMatchTeamTable(String matchTeamId);

    // 외래키 추가
    void addForeignKeyConstraintToMatchTeam(Long fieldId);
}