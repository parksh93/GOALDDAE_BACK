package com.goalddae.controller;

import com.goalddae.dto.soccerField.DeleteSoccerFieldListDTO;
import com.goalddae.dto.fieldReservation.FieldReservationInfoDTO;
import com.goalddae.dto.soccerField.SoccerFieldDTO;
import com.goalddae.dto.soccerField.SoccerFieldInfoDTO;
import com.goalddae.entity.SoccerField;
import com.goalddae.service.SoccerFieldService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
    @PutMapping("/save")
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
    @PatchMapping("/update")
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
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteSoccerField(@RequestBody DeleteSoccerFieldListDTO deleteSoccerFieldListDTO) {
        try {
            for (long soccerFieldId:deleteSoccerFieldListDTO.getSoccerFieldList()) {
                soccerFieldService.delete(soccerFieldId);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // 예약할 구장 리스트 조회
    @GetMapping("/reservation/list")
    public ResponseEntity<Page<SoccerFieldDTO>> findReservationField(
            @RequestParam(required = false) Long userId,
            @RequestParam String province,
            @RequestParam String inOutWhether,
            @RequestParam String grassWhether,
            @RequestParam LocalDate reservationDate,
            @RequestParam String reservationPeriod,
            @RequestParam int pageNumber,
            @RequestParam int pageSize) {

        Page<SoccerFieldDTO> availableFields = soccerFieldService.findAvailableField(Optional.ofNullable(userId), province, inOutWhether, grassWhether, reservationDate, reservationPeriod, pageNumber, pageSize);
        return ResponseEntity.ok(availableFields);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {

        long maxSize = 20000 * 1024;

        if (file.getSize() > maxSize) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일 크기가 초과되었습니다.");
        }

        String imageUrl = soccerFieldService.uploadImage(file);
        return ResponseEntity.ok(imageUrl);
    }
}