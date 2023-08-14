package com.goalddae.service;

import com.goalddae.entity.SoccerField;
import com.goalddae.repository.SoccerFieldRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SoccerFieldService {

    private final SoccerFieldRepository soccerFieldRepository;

    public SoccerFieldService(SoccerFieldRepository soccerFieldRepository) {
        this.soccerFieldRepository = soccerFieldRepository;
    }

    public List<SoccerField> searchSoccerFields(String searchTerm) {
        return soccerFieldRepository.findByRegionContainingOrFieldNameContaining(searchTerm, searchTerm);
    }

    // 지역 조회
    public List<String> searchCityNames(String searchTerm) {
        List<String> cityNames = new ArrayList<>();
        cityNames.add("서울");
        cityNames.add("부산");
        cityNames.add("인천");
        cityNames.add("대구");
        cityNames.add("울산");
        cityNames.add("광주");
        cityNames.add("대전");
        cityNames.add("성남");
        cityNames.add("수원");
        cityNames.add("고양");
        cityNames.add("광명");
        cityNames.add("광주");
        cityNames.add("과천");
        cityNames.add("구리");
        cityNames.add("군포");
        cityNames.add("김포");
        cityNames.add("남양주");
        cityNames.add("부천");
        cityNames.add("시흥");
        cityNames.add("안산");
        cityNames.add("안성");
        cityNames.add("안양");
        cityNames.add("양주");
        cityNames.add("여주");
        cityNames.add("오산");
        cityNames.add("용인");
        cityNames.add("의왕");
        cityNames.add("의정부");
        cityNames.add("이천");
        cityNames.add("파주");
        cityNames.add("평택");
        cityNames.add("포천");
        cityNames.add("하남");
        cityNames.add("화성");
        cityNames.add("동두천");

        List<String> matchedCityNames = new ArrayList<>();
        for (String cityName : cityNames) {
            if (cityName.startsWith(searchTerm)) {
                matchedCityNames.add(cityName);
            }
        }

        return matchedCityNames;
    }
}
