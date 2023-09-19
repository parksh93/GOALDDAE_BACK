package com.goalddae.service;

import com.goalddae.dto.match.TeamMatchRequestDTO;

public interface TeamMatchRequestService {
    void requestTeamMatch(Long userId, TeamMatchRequestDTO request);
}