package com.goalddae.controller;

import com.goalddae.dto.team.TeamMemberDTO;
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
    public TeamMemberController(TeamMemberService teamMemberService){
        this.teamMemberService = teamMemberService;
    }

    @GetMapping(value = "/check")
    public List<TeamMemberDTO> memberList(){
        List<TeamMemberDTO> member = teamMemberService.findAll();
        return member;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addTeamMember(@RequestBody TeamMemberDTO teamMemberDTO){
        try{
            teamMemberService.addTeamMember(teamMemberDTO);

            long id = teamMemberDTO.getId();
            return ResponseEntity.ok(id);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping(value = "/removeMember")
    public ResponseEntity<?> removeTeamMember(@PathVariable long id){
        teamMemberService.deleteTeamMemberById(id);
        return ResponseEntity.ok("팀 멤버가 정상적으로 삭제되었습니다.");
    }
}
