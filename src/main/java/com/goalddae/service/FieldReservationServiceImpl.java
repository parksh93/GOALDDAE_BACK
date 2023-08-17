package com.goalddae.service;

import com.goalddae.repository.FieldReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FieldReservationServiceImpl implements FieldReservationService {

    private final FieldReservationRepository fieldReservationRepository;

    @Autowired
    public FieldReservationServiceImpl(FieldReservationRepository fieldReservationRepository) {
        this.fieldReservationRepository = fieldReservationRepository;
    }

    @Override
    @Transactional
    public void createFieldReservationTable(String fieldReservation) {
        fieldReservationRepository.dropFieldReservationTable(fieldReservation);
        fieldReservationRepository.createFieldReservationTable(fieldReservation);
    }
}
