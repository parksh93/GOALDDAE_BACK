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

    // 동적테이블 생성 - 구장 예약
    @Override
    @Transactional
    public boolean createFieldReservationTable(Long fieldId) {
        try {
            String safeTable = MyBatisUtil.safeTable(fieldId);
            fieldReservationRepository.createFieldReservationTable(safeTable);
            return true;
        } catch (Exception e) {
            System.out.println("테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }

    // 동적테이블 삭제 - 구장 예약
    @Override
    @Transactional
    public boolean dropFieldReservationTable(Long fieldId) {
        try {
            String safeTable = MyBatisUtil.safeTable(fieldId);
            fieldReservationRepository.dropFieldReservationTable(safeTable);
            return true;
        } catch (Exception e) {
            System.out.println("테이블 삭제 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }
}