package com.goalddae.service;

import com.goalddae.entity.SoccerFiled;
import com.goalddae.repository.SoccerFiledRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoccerFieldService {

    private final SoccerFiledRepository soccerFiledRepository;

    public SoccerFieldService(SoccerFiledRepository soccerFiledRepository) {
        this.soccerFiledRepository = soccerFiledRepository;
    }

    public List<SoccerFiled> searchSoccerFields(String keyword) {
        return soccerFiledRepository.findByRegion(keyword);
    }
}
