package com.goalddae.service;

import com.goalddae.entity.Team;
import org.apache.ibatis.annotations.Param;

public interface TeamService {
    boolean createTeamMemberTable(@Param("teamId") Long teamId);
    boolean createTeamApplyTable(@Param("teamId") Long teamId);
    boolean createTeamMatchResult(@Param("teamId") Long teamId);
    void save(Team team);
}
