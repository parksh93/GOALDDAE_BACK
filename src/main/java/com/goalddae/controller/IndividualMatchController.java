package com.goalddae.controller;

import com.goalddae.dto.individualMatch.IndividualMatchDTO;
import com.goalddae.service.IndividualMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/individual-match")
public class IndividualMatchController {

    @Autowired
    private IndividualMatchService individualMatchService;

    @GetMapping
    public ResponseEntity<List<IndividualMatchDTO>> findAll() {
        List<IndividualMatchDTO> individualMatchList = individualMatchService.findAll();
        return new ResponseEntity<>(individualMatchList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IndividualMatchDTO> findById(@PathVariable("id") Long id) {
        IndividualMatchDTO individualMatchDTO = individualMatchService.findById(id);
        return new ResponseEntity<>(individualMatchDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody IndividualMatchDTO individualMatchDTO) {
        individualMatchService.save(individualMatchDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody IndividualMatchDTO individualMatchDTO) {
        individualMatchService.update(individualMatchDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        individualMatchService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}