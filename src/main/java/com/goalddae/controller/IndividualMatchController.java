package com.goalddae.controller;

import com.goalddae.dto.match.IndividualMatchDTO;
import com.goalddae.service.IndividualMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/match")
public class IndividualMatchController {

    private final IndividualMatchService individualMatchService;

    @Autowired
    public IndividualMatchController(IndividualMatchService individualMatchService) {
        this.individualMatchService = individualMatchService;
    }

    // 개인매치리스트 조회
    @GetMapping("/individual")
    public List<IndividualMatchDTO> getMatches(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return individualMatchService.getMatchesByDate(date);
    }
}
