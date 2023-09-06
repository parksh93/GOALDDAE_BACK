package com.goalddae.controller;

import com.goalddae.dto.fieldReservation.FieldReservationDTO;
import com.goalddae.service.FieldReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
public class FieldReservationController {

    FieldReservationService fieldReservationService;

    @Autowired
    public FieldReservationController(FieldReservationService fieldReservationService) {
        this.fieldReservationService = fieldReservationService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createReservationFieldAndMatch(@RequestBody FieldReservationDTO dto) {
        fieldReservationService.CreateReservationFieldAndMatch(dto);
        return ResponseEntity.ok("Reservation created successfully");
    }

}
