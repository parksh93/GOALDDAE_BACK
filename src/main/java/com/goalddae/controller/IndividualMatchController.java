package com.goalddae.controller;

import com.goalddae.dto.match.IndividualMatchDTO;
import com.goalddae.dto.match.IndividualMatchRequestDTO;
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
import java.util.ArrayList;
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
        List<Object> combinedList = individualMatchService.findAllByUserId(userId);
        List<IndividualMatchRequestDTO> result = new ArrayList<>();

        for (Object item : combinedList) {
            if (item instanceof IndividualMatchRequest) {
                // IndividualMatchRequest를 IndividualMatchRequestDTO로 변환
                IndividualMatchRequestDTO requestDTO = convertToDto((IndividualMatchRequest) item);
                result.add(requestDTO);
            } else if (item instanceof IndividualMatch) {
                // IndividualMatch를 IndividualMatchRequestDTO로 변환
                IndividualMatchRequestDTO matchDTO = convertToDto((IndividualMatch) item);
                result.add(matchDTO);
            }
        }

        return result;
    }

    public IndividualMatchRequestDTO convertToDto(IndividualMatchRequest matchRequest) {
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

    public IndividualMatchRequestDTO convertToDto(IndividualMatch individualMatch) {
        return IndividualMatchRequestDTO.builder()
                .id(individualMatch.getId())
                .playerNumber(individualMatch.getPlayerNumber())
                .level(individualMatch.getLevel())
                .gender(individualMatch.getGender())
                .startTime(individualMatch.getStartTime())
                .endTime(individualMatch.getEndTime())
                .soccerField(individualMatch.getReservationField().getSoccerField())
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