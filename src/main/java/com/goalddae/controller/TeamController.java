package com.goalddae.controller;

import com.goalddae.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // 팀멤버 테이블 생성
    @PostMapping("/team-member/create-table")
    public ResponseEntity<String> teamMemberCreateTable(@RequestParam("teamMember") String teamMember) {
        try {
            String decodedTeamMember = URLDecoder.decode(teamMember, StandardCharsets.UTF_8);
            teamService.createTeamMemberTable(decodedTeamMember);
            return new ResponseEntity<>("테이블 생성 완료: " + decodedTeamMember, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("테이블 생성 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 팀 가입신청 테이블 생성
    @PostMapping("/team-apply/create-table")
    public ResponseEntity<String> teamApplyCreateTable(@RequestParam("teamApply") String teamApply) {
        try {
            String decodedTeamApply = URLDecoder.decode(teamApply, StandardCharsets.UTF_8);
            teamService.createTeamApplyTable(decodedTeamApply);
            return new ResponseEntity<>("테이블 생성 완료: " + decodedTeamApply, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("테이블 생성 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
