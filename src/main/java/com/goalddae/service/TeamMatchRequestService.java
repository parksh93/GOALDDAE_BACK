package com.goalddae.service;

import com.goalddae.dto.match.TeamMatchRequestDTO;
import com.goalddae.dto.user.TeamMatchUserInfoDTO;
import com.goalddae.entity.User;

import java.util.List;

public interface TeamMatchRequestService {
    void requestTeamMatch(Long userId, Long teamMatchId, TeamMatchRequestDTO request);
    List<TeamMatchUserInfoDTO> getHomeRequest(Long matchId);
    List<TeamMatchUserInfoDTO> getAwayRequest(Long matchId);
}