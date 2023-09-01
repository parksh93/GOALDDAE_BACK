package com.goalddae.controller;

import com.goalddae.dto.soccerField.SoccerFieldDTO;
import com.goalddae.dto.soccerField.SoccerFieldInfoDTO;
import com.goalddae.entity.SoccerField;
import com.goalddae.service.SoccerFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/field")
public class SoccerFieldController {
    SoccerFieldService soccerFieldService;

    @Autowired
    public SoccerFieldController(SoccerFieldService soccerFieldService){
        this.soccerFieldService = soccerFieldService;
    }

    @RequestMapping("/getFieldInfo/{fieldId}")
    public ResponseEntity<SoccerFieldInfoDTO> getFieldInfo(@PathVariable long fieldId){
        SoccerFieldInfoDTO soccerFieldInfoDTO = soccerFieldService.findById(fieldId);
        return ResponseEntity.ok(soccerFieldInfoDTO);
    }

    // 구장 검색
    @GetMapping("/search")
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
    @PostMapping("/save")
    public ResponseEntity<SoccerField> saveSoccerField(@RequestBody SoccerField soccerField) {
        try {
            SoccerField saveSoccerField = soccerFieldService.save(soccerField);
            return ResponseEntity.ok(saveSoccerField);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // 구장 수정
    @PutMapping("/update")
    public ResponseEntity<SoccerField> updateSoccerField(@RequestBody SoccerFieldDTO soccerFieldDTO) {
        try {
            SoccerField updatedSoccerField = soccerFieldService.update(soccerFieldDTO);
            return ResponseEntity.ok(updatedSoccerField);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // 구장 삭제
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteSoccerField(@RequestBody SoccerField soccerField) {
        try {
            soccerFieldService.delete(soccerField.getId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // 모든 도시 이름 조회
    @GetMapping("/provinces")
    public ResponseEntity<List<String>> getProvinces() {
        try {
            List<String> provinces = soccerFieldService.getProvinces();
            return ResponseEntity.ok(provinces);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // 타임라인 - 필터기능
    @GetMapping("/search/province")
    public ResponseEntity<List<SoccerField>> searchFieldByProvince(
            @RequestParam(value = "province", required = false, defaultValue = "") String province) {
        try {
            List<SoccerField> fields = soccerFieldService.searchFieldByProvince(province);
            return ResponseEntity.ok(fields);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}