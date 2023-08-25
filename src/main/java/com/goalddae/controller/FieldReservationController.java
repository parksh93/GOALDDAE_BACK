package com.goalddae.controller;

import com.goalddae.service.FieldReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/field-reservation")
public class FieldReservationController {
    private final FieldReservationService fieldReservationService;

    public FieldReservationController(FieldReservationService fieldReservationService) {
        this.fieldReservationService = fieldReservationService;
    }

    // 구장 예약 테이블 생성
    @PostMapping("/create-table")
    public ResponseEntity<String> createTable(@RequestParam("fieldReservation") String fieldReservation) {
        try {
            String decodedFieldReservation = URLDecoder.decode(fieldReservation, StandardCharsets.UTF_8);
            fieldReservationService.createFieldReservationTable(decodedFieldReservation);
            return new ResponseEntity<>("테이블 생성 완료: " + decodedFieldReservation, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("테이블 생성 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
