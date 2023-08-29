package com.goalddae.controller;

import com.goalddae.entity.Team;
import com.goalddae.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
public class TeamController {

    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    // 팀 등록
    @PostMapping(value = "/save")
    public ResponseEntity<String> teamSave(@RequestBody Team team){
        teamService.save(team);
        return ResponseEntity
                .ok("팀 생성이 완료되었습니다.");
    }
}
