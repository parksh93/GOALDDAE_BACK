package com.goalddae.service;

import com.goalddae.entity.TeamMatch;
import com.goalddae.entity.TeamMatchRequest;

public interface TeamMatchService {

    void createTeamMatch(TeamMatch teamMatch);

    void requestTeamMatch(TeamMatchRequest request);
}