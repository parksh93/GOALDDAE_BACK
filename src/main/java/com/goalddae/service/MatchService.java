package com.goalddae.service;

import com.goalddae.dto.match.MatchIndividualDTO;

import java.util.List;

public interface MatchService {
    void createMatchTeamTable(String matchTeam);
    void createMatchIndividualTable(String matchIndividual);
    List<MatchIndividualDTO> findAllByUserId(String userId);

}

