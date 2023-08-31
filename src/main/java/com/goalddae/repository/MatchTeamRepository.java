package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MatchTeamRepository {
    void createMatchTeamTable(@Param("id") Long matchTeamId);
    void dropMatchTeamTable(@Param("id") Long matchTeamId);
}