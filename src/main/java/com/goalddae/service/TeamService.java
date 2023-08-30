package com.goalddae.service;

import com.goalddae.dto.team.TeamSaveDTO;
import com.goalddae.entity.Team;
import org.apache.ibatis.annotations.Param;

public interface TeamService {
    boolean createTeamMemberTable(@Param("teamId") Long teamId);
    boolean createTeamApplyTable(@Param("teamId") Long teamId);
    boolean createTeamMatchResult(@Param("teamId") Long teamId);
    void save(TeamSaveDTO teamSaveDTO);
}
