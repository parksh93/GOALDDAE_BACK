package com.goalddae.service;

import com.goalddae.entity.Team;

public interface TeamService {
    void createTeamMemberTable(String teamMember);
    void createTeamApplyTable(String teamApply);
    void createTeamMatchResult(String teamMatchResult);
    void save(Team team);
}
