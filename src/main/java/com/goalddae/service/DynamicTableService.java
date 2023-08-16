package com.goalddae.service;

import com.goalddae.repository.IndividualMatchRepository;
import com.goalddae.repository.FieldReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamicTableService {

    @Autowired
    private IndividualMatchRepository individualMatchRepository;

    @Autowired
    private FieldReservationRepository fieldReservationRepository;

    public void createIndividualMatchTable(String individualMatchTableName) {
        individualMatchRepository.individualMatch(individualMatchTableName);
    }

    public void createFieldReservationTable(String fieldReservationTableName) {
        fieldReservationRepository.fieldReservation(fieldReservationTableName);
    }
}
