package com.goalddae.controller;

import com.goalddae.entity.SoccerField;
import com.goalddae.service.SoccerFieldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class MainController {

    private final SoccerFieldService soccerFieldService;

    public MainController(SoccerFieldService soccerFieldService) {
        this.soccerFieldService = soccerFieldService;
    }

    // 구장 검색
    @GetMapping("/search/soccerField")
    public List<SoccerField> searchSoccerFields(
            @RequestParam(value = "searchTerm", required = false, defaultValue = "") String searchTerm) {
        return soccerFieldService.searchSoccerFields(searchTerm);
    }

    // 클라이언트가 전달한 searchTerm 매개변수를 받아 SoccerFieldService의 searchCityNames() 메서드를 호출
    // 전달받은 searchTerm과 일치하는 도시 목록을 반환
    @GetMapping("/search/city")
    public ResponseEntity<List<String>> searchCityNames(
            @RequestParam(value = "searchTerm", required = false, defaultValue = "") String searchTerm) {
        try {
            List<String> cityNames = soccerFieldService.searchCityNames(searchTerm);
            return ResponseEntity.ok(cityNames);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // 구장 등록
    @PostMapping("/addSoccerField")
    public ResponseEntity<SoccerField> addSoccerField(@RequestBody SoccerField soccerField) {
        try {
            SoccerField addedField = soccerFieldService.addSoccerField(soccerField);
            return ResponseEntity.ok(addedField);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}