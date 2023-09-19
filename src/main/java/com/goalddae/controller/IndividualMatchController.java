package com.goalddae.controller;

import com.goalddae.dto.match.*;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.entity.IndividualMatchRequest;
import com.goalddae.service.IndividualMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/my-individual/{userId}")
    public List<IndividualMatchRequestDTO> getIndividualMatchesRequest(@PathVariable long userId) {
        return individualMatchService.findAllByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public IndividualMatchRequestDTO convertToDto(IndividualMatchRequest  matchRequest) {
        return IndividualMatchRequestDTO.builder()
                .id(matchRequest.getIndividualMatch().getId())
                .playerNumber(matchRequest.getIndividualMatch().getPlayerNumber())
                .level(matchRequest.getIndividualMatch().getLevel())
                .gender(matchRequest.getIndividualMatch().getGender())
                .startTime(matchRequest.getIndividualMatch().getStartTime())
                .endTime(matchRequest.getIndividualMatch().getEndTime())
                .soccerField(matchRequest.getIndividualMatch().getReservationField().getSoccerField())
                .build();
    }

    @GetMapping("/individual/detail/{matchId}")
    public IndividualMatchDetailDTO getIndividualDetail(@PathVariable long matchId){
        return individualMatchService.findById(matchId);
    }

    @GetMapping("/individual/getPlayer/{matchId}")
    public List<GetPlayerInfoDTO> getIndividualPlayer(@PathVariable long matchId){
        return individualMatchService.getMatchPlayerInfo(matchId);
    }

    @PutMapping("/individual/request")
    public void saveMatchRequest(@RequestBody SaveIndividualMatchDTO saveIndividualMatchDTO){
        individualMatchService.saveMatchRequest(saveIndividualMatchDTO);
    }

    @DeleteMapping("/individual/cancelRequest")
    public void cancelMatchRequest(@RequestBody CancelMatchRequestDTO cancelMatchRequestDTO){
        individualMatchService.cancelMatchRequest(cancelMatchRequestDTO);
    }

}