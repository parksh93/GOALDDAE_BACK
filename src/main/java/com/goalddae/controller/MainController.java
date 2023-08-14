package com.goalddae.controller;

import com.goalddae.entity.SoccerField;
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

    @GetMapping("/search/soccerField")
    public List<SoccerField> searchSoccerFields(
            @RequestParam(value = "searchTerm", required = false, defaultValue = "") String searchTerm) {
        return soccerFieldService.searchSoccerFields(searchTerm);
    }

    @GetMapping("/search/city") // 추가
    public List<String> searchCityNames(
            @RequestParam(value = "searchTerm", required = false, defaultValue = "") String searchTerm) { // 추가
        return soccerFieldService.searchCityNames(searchTerm); // 추가
    }
}