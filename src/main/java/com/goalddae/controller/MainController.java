package com.goalddae.controller;

import com.goalddae.entity.SoccerFiled;
import com.goalddae.service.SoccerFieldService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    private final SoccerFieldService soccerFieldService;

    public MainController(SoccerFieldService soccerFieldService) {
        this.soccerFieldService = soccerFieldService;
    }

    @GetMapping("/search/soccerFiled")
    public List<SoccerFiled> searchSoccerFields(@RequestParam String keyword) {
        return soccerFieldService.searchSoccerFields(keyword);
    }

}
