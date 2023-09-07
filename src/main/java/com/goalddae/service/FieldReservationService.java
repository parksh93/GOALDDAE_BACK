package com.goalddae.service;

import com.goalddae.dto.fieldReservation.FieldReservationDTO;

import java.util.List;

public interface FieldReservationService {

    void CreateReservationFieldAndMatch(FieldReservationDTO fieldReservationDTO);

    List<Integer> getReservationTimesByFieldIdAndDate(long fieldId, String date);

}