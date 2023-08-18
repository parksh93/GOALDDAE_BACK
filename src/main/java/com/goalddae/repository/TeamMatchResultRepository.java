package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamMatchResultRepository {
    void createTeamMatchResultTable(String teamMatchResult);
}
