package com.goalddae.service;

import com.goalddae.dto.manager.ManagerIndividualMatchDTO;
import com.goalddae.dto.manager.ManagerUserInfoDTO;
import com.goalddae.entity.IndividualMatch;

import java.util.List;

public interface ManagerService {

    List<ManagerIndividualMatchDTO> getFinishedMatches(Long managerId);

    List<ManagerUserInfoDTO> getMatchParticipants(Long matchId);

    void increaseNoShowCount(List<Long> userIds);

}
