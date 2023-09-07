package com.goalddae.controller;

import com.goalddae.dto.fieldReservation.FieldReservationDTO;
import com.goalddae.service.FieldReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/times")
    public List<Integer> getReservationTimes(@RequestParam long fieldId, @RequestParam String date) {
        return fieldReservationService.getReservationTimesByFieldIdAndDate(fieldId, date);
    }

}
