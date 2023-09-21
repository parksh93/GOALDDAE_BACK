package com.goalddae.repository;

import com.goalddae.dto.manager.ManagerTeamMatchResultDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TeamMatchResultRepository {
    void createTeamMatchResultTable(@Param("teamId") Long teamId);

    void insertHomeTeamMatchResult(ManagerTeamMatchResultDTO managerTeamMatchResultDTO);

    void insertAwayTeamMatchResult(ManagerTeamMatchResultDTO managerTeamMatchResultDTO);
}
