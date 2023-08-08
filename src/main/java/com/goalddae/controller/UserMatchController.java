package com.goalddae.controller;

import com.goalddae.entity.Match;
import com.goalddae.exception.NotFoundMatchException;
import com.goalddae.service.UserMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mypage/match")
public class UserMatchController {

    @Autowired
    UserMatchService userMatchService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Match>> getMatchById(@PathVariable long userId) {

        try {
            List<Match> matchList = userMatchService.findMatchById(userId);
            return ResponseEntity.ok(matchList);

        } catch (NotFoundMatchException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
