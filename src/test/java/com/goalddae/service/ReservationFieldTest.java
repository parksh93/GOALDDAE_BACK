package com.goalddae.service;

import com.goalddae.dto.soccerField.SoccerFieldDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
public class ReservationFieldTest {

    @Autowired
    private FieldReservationService fieldReservationService;

    @Test
    @Transactional
    @DisplayName("예약 가능한 구장 조회")
    public void findAvailableReservationField() {
    }
}
