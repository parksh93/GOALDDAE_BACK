package com.goalddae.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.entity.SoccerField;
import com.goalddae.repository.SoccerFieldRepository;
import org.springframework.stereotype.Service;

@Service
public class SoccerFieldService {

    private final SoccerFieldRepository soccerFieldRepository;
    private final TeamService teamService;
    private final FieldReservationService fieldReservationService;

    public SoccerFieldService(SoccerFieldRepository soccerFieldRepository,
                              TeamService teamService,
                              FieldReservationService fieldReservationService) {
        this.soccerFieldRepository = soccerFieldRepository;
        this.teamService = teamService;
        this.fieldReservationService = fieldReservationService;
    }

    // 축구장 조회
    public List<SoccerField> searchSoccerFields(String searchTerm) {
        return soccerFieldRepository.findByRegionContainingOrFieldNameContaining(searchTerm, searchTerm);
    }

    // 지역 조회
    public List<String> searchCityNames(String searchTerm) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("cityNames.json");

        ObjectMapper objectMapper = new ObjectMapper();

        // 변환 된 cityNames.json 파일의 도시 이름들이 cityNames리스트에 저장됨
        List<String> cityNames = Arrays.asList(objectMapper.readValue(inputStream, String[].class));

        // ArrayList 객체를 생성하여 searchTerm과 일치하는 도시 이름들이 저장됨
        List<String> matchedCityNames = new ArrayList<>();
        for (String cityName : cityNames) {
            if (cityName.startsWith(searchTerm)) {
                matchedCityNames.add(cityName);
            }
        }
        // searchTerm과 일치하는 도시 이름들을 반환
        return matchedCityNames;
    }

    // 구장 테이블 추가 시 멤버, 수락, 매치결과 동적테이블 같이 생성되게끔
    public SoccerField addSoccerField(SoccerField soccerField) {
        SoccerField newSoccerField = soccerFieldRepository.save(soccerField);
        String fieldName = newSoccerField.getFieldName();

        fieldReservationService.createFieldReservationTable("field_reservation_" + fieldName);
        teamService.createTeamMemberTable("team_member_" + fieldName);
        teamService.createTeamApplyTable("team_apply_" + fieldName);
        teamService.createTeamMatchResult("team_match_result_" + fieldName);

        return newSoccerField;
    }
}
