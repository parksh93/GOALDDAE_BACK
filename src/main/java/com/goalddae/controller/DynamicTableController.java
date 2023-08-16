package com.goalddae.controller;

import com.goalddae.repository.IndividualMatchRepository;
import com.goalddae.repository.FieldReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DynamicTableController {

    @Autowired
    private IndividualMatchRepository individualMatchRepository;

    @Autowired
    private FieldReservationRepository fieldReservationRepository;

    @GetMapping("/createIndividualMatchTable")
    public void createIndividualMatchTable(@RequestParam String individualMatch) {
        individualMatchRepository.individualMatch(individualMatch);
    }

    @GetMapping("/createFieldReservationTable")
    public void createFieldReservationTable(@RequestParam String fieldReservation) {
        fieldReservationRepository.fieldReservation(fieldReservation);
    }
}
