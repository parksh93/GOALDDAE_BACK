package com.goalddae.service;

import com.goalddae.dto.match.*;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.entity.IndividualMatchRequest;
import com.goalddae.entity.ReservationField;
import com.goalddae.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public interface IndividualMatchService {
    List<IndividualMatchDTO> getMatchesByDateAndProvinceAndLevelAndGender(LocalDate date,
                                                                          String province,
                                                                          String level,
                                                                          String gender,
                                                                          Long  lastMatchId);

    List<Object> findAllByUserId(long userId);
    IndividualMatchDetailDTO findById(long matchId);
    void saveMatchRequest(SaveIndividualMatchDTO saveIndividualMatchDTO);
    List<GetPlayerInfoDTO> getMatchPlayerInfo(long matchId);
    void cancelMatchRequest(CancelMatchRequestDTO cancelMatchRequestDTO);

}