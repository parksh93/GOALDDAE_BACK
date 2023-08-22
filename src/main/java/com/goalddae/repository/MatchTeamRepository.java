package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MatchTeamRepository {
    void createMatchTeamTable(String matchTeam);
    void addForeignKeyConstraintToMatchTeam(String fieldName);

}