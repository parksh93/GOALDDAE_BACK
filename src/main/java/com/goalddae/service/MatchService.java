package com.goalddae.service;

import com.goalddae.dto.match.MatchIndividualDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface MatchService {
    void createMatchTeamTable(String matchTeam);
    void createMatchIndividualTable(String matchIndividual);
    List<MatchIndividualDTO> findIndividualMatches(String matchIndividualTable,
                                                   LocalDateTime recruitStart,
                                                   LocalDateTime recruitEnd);
}