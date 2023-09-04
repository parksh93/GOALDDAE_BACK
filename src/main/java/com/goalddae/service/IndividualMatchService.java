package com.goalddae.service;

import com.goalddae.dto.match.IndividualMatchDTO;
import com.goalddae.entity.ReservationField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IndividualMatchService {
    List<IndividualMatchDTO> getMatchesByDateAndProvince(LocalDate date, String province);
}
