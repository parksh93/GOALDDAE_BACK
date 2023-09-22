package com.goalddae.controller;

import com.goalddae.dto.match.TeamMatchRequestDTO;
import com.goalddae.dto.team.*;
import com.goalddae.dto.match.TeamMatchDTO;
import com.goalddae.dto.team.TeamListDTO;
import com.goalddae.dto.team.TeamUpdateDTO;

import com.goalddae.entity.Team;
import com.goalddae.entity.TeamMatchRequest;
import com.goalddae.service.TeamMatchService;
import org.springframework.beans.factory.annotation.Autowired;

import com.goalddae.dto.match.TeamMatchInfoDTO;
import com.goalddae.dto.user.TeamMatchUserInfoDTO;
import com.goalddae.service.*;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.goalddae.dto.team.TeamSaveDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final TeamMatchService teamMatchService;

    @Autowired
    public TeamController(TeamService teamService,
                          TeamMatchService teamMatchService,
                          TeamMatchRequestService teamMatchRequestService,
                          UserService userService) {
        this.teamService = teamService;
        this.teamMatchService = teamMatchService;
    }

    @GetMapping(value = "/list")
    public List<TeamListDTO> teamList(){
        List<TeamListDTO> team = teamService.findAll();
        return team;
    }

    @GetMapping(value = {"/myTeam/{id}", "/myTeamDetail/{id}"})
    public ResponseEntity<?> myTeam(@PathVariable Long id){
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

    // 팀 등록
    @PostMapping(value = "/save")
    public ResponseEntity<String> saveTeam(@RequestBody TeamSaveDTO teamSaveDTO) {
        try {
            teamService.save(teamSaveDTO);
            return ResponseEntity.ok("팀 생성이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAutoIncrementTeamId")
    public ResponseEntity<Long> getAutoIncrementTeamId(){
        Long autoIncrementValue = teamService.getAutoIncrementValue();

        if (autoIncrementValue != null ){
            return ResponseEntity.ok(autoIncrementValue);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value="/update", method= RequestMethod.PUT)
    public ResponseEntity<?> teamUpdate(@RequestBody TeamUpdateDTO teamUpdateDTO){
        System.out.println(teamUpdateDTO);

        try{
            teamService.update(teamUpdateDTO);
            return ResponseEntity
                    .ok("팀 수정이 완료되었습니다.");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("팀 수정 중 오류 발생");
        }
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

    @PostMapping(value = "/addApply")
    public ResponseEntity<?> addTeamApply(@RequestBody TeamApplyDTO teamApplyDTO){
        try{
            teamService.addTeamApply(teamApplyDTO);

            return ResponseEntity.ok("팀 가입신청 완료");
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value="/checkApply")
    public ResponseEntity<?> findStatus0ByTeamId(@RequestParam long teamId){
        try{
            List<TeamMemberCheckDTO> applys = teamService.findStatus0ByTeamId(teamId);
            return ResponseEntity.ok(applys);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/acceptApply")
    public ResponseEntity<?> acceptApply(@RequestBody TeamAcceptApplyDTO teamAcceptApplyDTO) {
        System.out.println(teamAcceptApplyDTO);

        try{
            teamService.acceptApply(teamAcceptApplyDTO);
            return ResponseEntity.ok("가입 수락");

        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("가입 수락 중 오류 발생");
        }

    }

    @RequestMapping(value = "/rejectApply", method = RequestMethod.PATCH)
    public ResponseEntity<?> rejectApply(@RequestBody TeamApplyDTO teamApplyDTO){

        try{
            teamService.rejectApply(teamApplyDTO);
            return ResponseEntity.ok("가입 거절");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("가입 거절 중 오류 발생");
        }
    }

    @GetMapping(value = "/match/list")
    public ResponseEntity<Page<TeamMatchDTO>> findTeamMatches(
            @RequestParam(required = false) Long userId,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam("province") String province,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam int page,
            @RequestParam int size) {

        Page<TeamMatchDTO> matches = teamMatchService.getTeamMatches(Optional.ofNullable(userId), startTime.toLocalDate(), province, gender, page, size);
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }

    // 팀 매치 상세 정보 조회
    @GetMapping("/match/detail/{teamMatchId}")
    public ResponseEntity<TeamMatchInfoDTO> getTeamMatchDetail(@PathVariable Long teamMatchId) {
        return new ResponseEntity<>(teamMatchService.getTeamMatchDetail(teamMatchId), HttpStatus.OK);
    }

    // 팀 매치 신청
    @PutMapping("/match/request/{teamMatchId}")
    public ResponseEntity<Void> applyForMatch(
            @PathVariable Long teamMatchId,
            @RequestBody TeamMatchRequestDTO request) {

        teamMatchService.applyForMatch(teamMatchId, request);
        return ResponseEntity.ok().build();
    }
}