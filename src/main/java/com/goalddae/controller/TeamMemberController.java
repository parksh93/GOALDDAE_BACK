package com.goalddae.controller;

import com.goalddae.dto.team.TeamMemberCheckDTO;
import com.goalddae.dto.team.TeamMemberDTO;
import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teamMember")
public class TeamMemberController {
    private TeamMemberService teamMemberService;

    @Autowired
    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> teamMemberList(@RequestParam long teamId) {
        try {
            List<TeamMemberCheckDTO> members = teamMemberService.findAllTeamMembersByTeamId(teamId);
            return ResponseEntity.ok(members);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/checkManager")
    public ResponseEntity<Integer> checkManager(@RequestParam long userId,
                                                @RequestParam long teamId) {
        int manager = teamMemberService.findTeamManagerByUserId(userId, teamId);
        return ResponseEntity.ok(manager);
    }


    @PostMapping(value = "/add")
    public ResponseEntity<String> addTeamMember(@RequestBody TeamMemberDTO teamMemberDTO) {

        try {
            teamMemberService.addTeamMember(teamMemberDTO);
            return ResponseEntity.ok("팀 멤버가 추가되었습니다.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/remove")
    public ResponseEntity<?> removeTeamMember(@PathVariable long usersId,
                                              @PathVariable long teamId,
                                              @RequestBody GetUserInfoDTO getUserInfoDTO){
        try {
            teamMemberService.removeTeamMember(usersId, teamId, getUserInfoDTO);
            return ResponseEntity.ok("팀 멤버가 삭제되었습니다.");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
