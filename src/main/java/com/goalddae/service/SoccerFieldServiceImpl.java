package com.goalddae.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.entity.SoccerField;
import com.goalddae.repository.SoccerFieldRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SoccerFieldServiceImpl implements SoccerFieldService {

    private final SoccerFieldRepository soccerFieldRepository;
    private final FieldReservationService fieldReservationService;
    private final MatchService matchService;

    public SoccerFieldServiceImpl(SoccerFieldRepository soccerFieldRepository,
                                  FieldReservationService fieldReservationService,
                                  MatchService matchService) {
        this.soccerFieldRepository = soccerFieldRepository;
        this.fieldReservationService = fieldReservationService;
        this.matchService = matchService;
    }

    // 구장 조회
    @Override
    public List<SoccerField> searchSoccerFields(String searchTerm) {
        return soccerFieldRepository.findByRegionContainingOrFieldNameContaining(searchTerm, searchTerm);
    }

    // 지역 조회
    @Override
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

    // 구장 객체 추가 시 개인매치, 팀매치, 구장예약 동적테이블 같이 생성되게끔
    // 객체 생성 중 에러가 발생해도 테이블에 객체가 추가되어 안되게끔 @Transactional 추가
    @Override
    @Transactional
    public SoccerField save(SoccerField soccerField) {
        SoccerField newSoccerField = soccerFieldRepository.save(soccerField);
        Long id = newSoccerField.getId();

        // 동적테이블 생성
        fieldReservationService.createFieldReservationTable(id);
        matchService.createMatchIndividualTable(id);
        matchService.createMatchTeamTable(id);

        // 외래 키 제약 추가
        matchService.addForeignKeyConstraintToMatchIndividual(id);
        matchService.addForeignKeyConstraintToMatchTeam(id);

        return newSoccerField;
    }

    // 구장 수정
    @Override
    public SoccerField update(SoccerField soccerField) {
        // 아이디에 해당하는 축구장이 존재하지 않는 경우 예외 발생
        SoccerField existingSoccerField = soccerFieldRepository.findById(soccerField.getId())
                .orElseThrow(() -> new RuntimeException("ID에 해당하는 축구장을 찾을 수 없습니다: " + soccerField.getId()));

        // 기존 엔티티의 필드를 제공된 객체에서 업데이트
        SoccerField updateSoccerFiled = SoccerField.builder()
                .fieldName(nullCheckOrDefault(soccerField.getFieldName(), existingSoccerField.getFieldName()))
                .toiletStatus(nullCheckOrDefault(soccerField.getToiletStatus(), existingSoccerField.getToiletStatus()))
                .showerStatus(nullCheckOrDefault(soccerField.getShowerStatus(), existingSoccerField.getShowerStatus()))
                .parkingStatus(nullCheckOrDefault(soccerField.getParkingStatus(), existingSoccerField.getParkingStatus()))
                .fieldSize(nullCheckOrDefault(soccerField.getFieldSize(), existingSoccerField.getFieldSize()))
                .fieldImg1(nullCheckOrDefault(soccerField.getFieldImg1(), existingSoccerField.getFieldImg1()))
                .fieldImg2(nullCheckOrDefault(soccerField.getFieldImg2(), existingSoccerField.getFieldImg2()))
                .fieldImg3(nullCheckOrDefault(soccerField.getFieldImg3(), existingSoccerField.getFieldImg3()))
                .reservationFee(nullCheckOrDefault(soccerField.getReservationFee(), existingSoccerField.getReservationFee()))
                .region(nullCheckOrDefault(soccerField.getRegion(), existingSoccerField.getRegion()))
                .build();

        return soccerFieldRepository.save(updateSoccerFiled);
    }

    // 수정 입력칸이 null 인 경우 기존값 사용, null이 아닌 경우 변경
    private <T> T nullCheckOrDefault(T inputValue, T defaultValue) {
        return inputValue != null ? inputValue : defaultValue;
    }



    // 구장 이름으로 조회
    @Override
    public SoccerField findSoccerFieldByName(String fieldName) {
        return soccerFieldRepository.findByFieldName(fieldName);
    }

    // 구장 객체 삭제
    @Override
    public void delete(long id) {
        SoccerField soccerField = soccerFieldRepository.findById(id).orElse(null);

        // 구장 테이블 삭제 전 동적 테이블 모두 삭제
        if (soccerField != null) {
            fieldReservationService.dropFieldReservationTable(id);
            matchService.dropMatchIndividualTable(id);
            matchService.dropMatchTeamTable(id);
        }

        // 구장 테이블에서 해당 구장 삭제
        soccerFieldRepository.deleteById(id);
    }
}
