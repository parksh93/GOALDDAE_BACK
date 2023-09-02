package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TeamApplyRepository {
    void createTeamApplyTable(@Param("teamId") Long teamId);
}
