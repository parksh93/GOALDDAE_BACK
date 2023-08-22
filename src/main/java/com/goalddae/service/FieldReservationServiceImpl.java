package com.goalddae.service;

import com.goalddae.repository.FieldReservationRepository;
import com.goalddae.util.MyBatisUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FieldReservationServiceImpl implements FieldReservationService {

    private final FieldReservationRepository fieldReservationRepository;

    public FieldReservationServiceImpl(FieldReservationRepository fieldReservationRepository) {
        this.fieldReservationRepository = fieldReservationRepository;
    }

    @Override
    @Transactional
    public void createFieldReservationTable(String fieldReservation) {
        String safeTable = MyBatisUtil.safeTable(fieldReservation);
        fieldReservationRepository.createFieldReservationTable(safeTable);
    }
}
