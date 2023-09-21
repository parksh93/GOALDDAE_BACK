package com.goalddae.service;

import com.goalddae.dto.manager.*;
import com.goalddae.entity.IndividualMatch;

import java.util.List;

public interface ManagerService {

    List<ManagerIndividualMatchDTO> getFinishedMatches(Long managerId);

    List<ManagerUserInfoDTO> getMatchParticipants(Long matchId);

    void increaseNoShowCount(List<Long> userIds);

    List<ManagerTeamMatchDTO> getFinishedTeamMatches(Long managerId);

    ManagerTeamInfoDTO getMatchTeamInfo(Long matchId);

    void addTeamMatchResult(ManagerTeamMatchResultDTO managerTeamMatchResultDTO);
}
