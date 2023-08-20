package com.goalddae.controller;

import com.goalddae.dto.soccerField.SoccerFieldInfoDTO;
import com.goalddae.service.SoccerFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
