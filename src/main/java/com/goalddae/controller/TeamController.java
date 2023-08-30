package com.goalddae.controller;

import com.goalddae.dto.team.TeamListDTO;
import com.goalddae.dto.team.TeamUpdateDTO;
import com.goalddae.entity.Team;
import com.goalddae.service.TeamServiceImpl;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamServiceImpl teamService;

    @Autowired
    public TeamController(TeamServiceImpl teamService){
        this.teamService = teamService;
    }

    @GetMapping(value = "/list")
    public List<TeamListDTO> teamList(){
        List<TeamListDTO> team = teamService.findAll();
        return team;
    }

    @GetMapping(value = {"/myTeam/{id}", "/myTeamDetail/{id}"})
    public Team myTeam(@PathVariable Long id){
        return teamService.findTeamById(id);
    }


    @GetMapping(value="/detail/{id}")
    public ResponseEntity<?> teamDetail(@PathVariable Long id) {
        try {
            Team team = teamService.findTeamById(id);
            if (team == null) {
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
            return ResponseEntity.ok(team);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 서버에러
        }
    }

    @PostMapping(value = "/teamSave")
    public ResponseEntity<String> teamSave(@RequestBody Team team){

        teamService.save(team);
        return ResponseEntity
                .ok("팀 생성이 완료되었습니다.");
    }

    @RequestMapping(value="/teamUpdate", method= {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<String> teamUpdate(@RequestBody TeamUpdateDTO teamUpdateDTO){

        teamService.update(teamUpdateDTO);
        return ResponseEntity
                .ok("팀 수정이 완료되었습니다.");
    }


    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> teamDelete(@PathVariable Long id){

        teamService.deleteTeamById(id);
        return ResponseEntity.ok("정상적으로 삭제되었습니다.");
    }

    @GetMapping(value="/search/teamName")
    public ResponseEntity<List<Team>> searchTeamName(@RequestParam(value = "searchTerm", required = false, defaultValue = "") String searchTerm ) {

        List<Team> searchResult = teamService.findByTeamName(searchTerm);
        return ResponseEntity.ok(searchResult);
    }

    @GetMapping(value = "/list/area")
    public List<TeamListDTO> filterArea(@RequestParam String area){

        if(area == "모든지역"){
            return teamService.findAll();
        }
        return teamService.findByArea(area);
    }

    @GetMapping(value = "/list/recruiting")
    public List<TeamListDTO> filterRecruiting(@RequestParam(required = false) boolean recruiting){

        return teamService.findByRecruiting(true);
    }

    @GetMapping(value = "/list/areaAndRecruiting")
    public List<TeamListDTO> filterAreaAndRecruiting(@RequestParam(required = false) String area,
                                              @RequestParam(required = false) boolean recruiting){
        return teamService.findByAreaAndRecruiting(area, true);
    }

}
