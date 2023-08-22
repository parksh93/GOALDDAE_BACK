package com.goalddae.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.dto.soccerField.SoccerFieldInfoDTO;
import com.goalddae.entity.SoccerField;
import com.goalddae.repository.SoccerFieldRepository;
import org.springframework.stereotype.Service;

@Service
public class SoccerFieldServiceImpl implements SoccerFieldService {

    private final SoccerFieldRepository soccerFieldRepository;

    public SoccerFieldServiceImpl(SoccerFieldRepository soccerFieldRepository) {
        this.soccerFieldRepository = soccerFieldRepository;
    }

    // 축구장 조회
    @Override
    public List<SoccerField> searchSoccerFields(String searchTerm) {
        return soccerFieldRepository.findByRegionContainingOrFieldNameContaining(searchTerm, searchTerm);
    }

    // 지역 조회
    @Override
    public List<String> searchCityNames(String searchTerm) throws IOException {
        // JSON 파일에서 도시 이름 목록 불러오기
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("cityNames.json");

        // 객체를 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // 인풋스트림으로부터 읽어온 Json 데이터를 String[] 타입으로 변환 후, List<String>으로 변환
        // 변환 된 cityNames.json 파일의 도시 이름들이 cityNames리스트에 저장됨
        List<String> cityNames = Arrays.asList(objectMapper.readValue(inputStream, String[].class));

        // ArrayList 객체를 생성하여 searchTerm과 일치하는 도시 이름들이 저장됨
        // 반복문 설명1 - cityNames 리스트의 각 도시 이름에 대해 반복문을 실행
        // 반복문 설명2 - 현재 도시 이름이 searchTerm으로 시작한다면
        // (cityName.startsWith(searchTerm)), 일치하는 도시 이름이므로
        // matchedCityNames 리스트에 추가
        List<String> matchedCityNames = new ArrayList<>();
        for (String cityName : cityNames) {
            if (cityName.startsWith(searchTerm)) {
                matchedCityNames.add(cityName);
            }
        }
        // 반복문이 끝나면 matchedCityNames 리스트에 저장됨
        // searchTerm과 일치하는 도시 이름들을 반환
        return matchedCityNames;
    }

    @Override
    public SoccerFieldInfoDTO findById(long id) {
        SoccerField soccerField = soccerFieldRepository.findById(id).get();
        return new SoccerFieldInfoDTO(soccerField);
    }
}
