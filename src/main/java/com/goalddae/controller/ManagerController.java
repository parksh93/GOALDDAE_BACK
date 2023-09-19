package com.goalddae.controller;

import com.goalddae.dto.manager.ManagerIndividualMatchDTO;
import com.goalddae.dto.manager.ManagerUserInfoDTO;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService){
        this.managerService = managerService;
    }

    @GetMapping("/finished-matches")
    public List<ManagerIndividualMatchDTO> getFinishedMatches(@RequestParam Long managerId) {
        return managerService.getFinishedMatches(managerId);
    }

    @GetMapping("/matchParticipants")
    public List<ManagerUserInfoDTO> getMatchParticipants(@RequestParam Long matchId) {
        return managerService.getMatchParticipants(matchId);
    }

    @PostMapping("/increaseNoShowCount")
    public ResponseEntity<String> increaseNoShowCount(@RequestBody List<Long> userIds) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        try {
            managerService.increaseNoShowCount(userIds);
            return ResponseEntity.ok("No-show count increased successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
