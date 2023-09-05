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

    // 타임라인(일자, 지역, 레벨, 남녀구분) - 개인매치 조회
    @GetMapping("/individual")
    public List<IndividualMatchDTO> getMatches(
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("province") String province,
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "gender", required = false) String gender
    ) {
        return individualMatchService.getMatchesByDateAndProvinceAndLevelAndGender(
                startTime.toLocalDate(), province, level, gender);
    }
}