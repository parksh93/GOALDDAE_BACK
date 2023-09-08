package com.goalddae.service;

import com.goalddae.dto.match.IndividualMatchDTO;
import com.goalddae.dto.match.IndividualMatchRequestDTO;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.entity.IndividualMatchRequest;
import com.goalddae.entity.ReservationField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IndividualMatchService {
    List<IndividualMatchDTO> getMatchesByDateAndProvinceAndLevelAndGender(LocalDate date,
                                                                          String province,
                                                                          String level,
                                                                          String gender);

    List<IndividualMatchRequest> findAllByUserId(long userId);
}
