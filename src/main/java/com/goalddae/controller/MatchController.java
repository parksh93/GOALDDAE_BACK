package com.goalddae.controller;

import com.goalddae.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    // 팀매치 테이블 생성
    @PostMapping("/match-team/create-table")
    public ResponseEntity<String> matchTeamCreateTable(@RequestParam("matchTeam") String matchTeam) {
        try {
            String decodedMatchTeam = URLDecoder.decode(matchTeam, StandardCharsets.UTF_8);
            matchService.createMatchTeamTable(decodedMatchTeam);
            return new ResponseEntity<>("테이블 생성 완료: " + decodedMatchTeam, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("테이블 생성 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 개인매치 테이블 생성
    @PostMapping("/match-individual/create-table")
    public ResponseEntity<String> matchIndividualCreateTable(@RequestParam("matchIndividual") String matchIndividual) {
        try {
            String decodedMatchIndividual = URLDecoder.decode(matchIndividual, StandardCharsets.UTF_8);
            matchService.createMatchIndividualTable(decodedMatchIndividual);
            return new ResponseEntity<>("테이블 생성 완료: " + decodedMatchIndividual, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("테이블 생성 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}