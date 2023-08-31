package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TeamMatchResultRepository {
    void createTeamMatchResultTable(@Param("teamId") Long teamId);
}
