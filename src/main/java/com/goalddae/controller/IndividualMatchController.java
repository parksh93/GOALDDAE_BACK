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
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/match")
public class IndividualMatchController {

    private final IndividualMatchService individualMatchService;

    @Autowired
    public IndividualMatchController(IndividualMatchService individualMatchService) {
        this.individualMatchService = individualMatchService;
    }

    // 타임라인 - 매치 조회
    @GetMapping("/individual")
    public List<IndividualMatchDTO> getMatches(
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("province") String province
    ) {
        return individualMatchService.getMatchesByDateAndProvince(startTime.toLocalDate(), province);
    }
}