package com.goalddae.repository;

import com.goalddae.dto.team.TeamApplyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TeamApplyRepository {
    void createTeamApplyTable(@Param("teamId") Long teamId);

    void addTeamApply(TeamApplyDTO teamApplyDTO);
}
