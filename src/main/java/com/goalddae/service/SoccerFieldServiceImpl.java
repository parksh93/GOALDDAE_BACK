package com.goalddae.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.dto.soccerField.SoccerFieldDTO;
import com.goalddae.dto.soccerField.SoccerFieldInfoDTO;
import com.goalddae.entity.SoccerField;
import com.goalddae.repository.SoccerFieldRepository;
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

    public SoccerFieldServiceImpl(SoccerFieldRepository soccerFieldRepository) {
        this.soccerFieldRepository = soccerFieldRepository;
    }

    // 구장 조회
    @Override
    public List<SoccerField> searchSoccerFields(String searchTerm) {
        return soccerFieldRepository.findByRegionContainingOrFieldNameContaining(searchTerm, searchTerm);
    }

    // 구장 이름으로 조회
    @Override
    public SoccerField findSoccerFieldByName(String fieldName) {
        return soccerFieldRepository.findByFieldName(fieldName);
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

    // 구장 객체 생성
    @Override
    @Transactional
    public SoccerField save(SoccerField soccerField) {
        SoccerField newSoccerField = soccerFieldRepository.save(soccerField);
        return newSoccerField;
    }

    // 구장 수정
    @Override
    @Transactional
    public SoccerField update(SoccerFieldDTO soccerFieldDto) {
        // 먼저 이전에 저장된 SoccerField 객체를 탐색
        SoccerField soccerField = soccerFieldRepository.findById(soccerFieldDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 구장이 존재하지 않습니다. id=" + soccerFieldDto.getId()));

        soccerField.changeFieldName(soccerFieldDto.getFieldName());
        soccerField.changeOperatingHours(soccerFieldDto.getOperatingHours());
        soccerField.changeClosingTime(soccerFieldDto.getClosingTime());
        soccerField.changeRegion(soccerFieldDto.getRegion());
        soccerField.changeFieldSize(soccerFieldDto.getFieldSize());
        soccerField.changeReservationFee(soccerFieldDto.getReservationFee());
        soccerField.changeParkingStatus(soccerFieldDto.isParkingStatus());
        soccerField.changeShowerStatus(soccerFieldDto.isShowerStatus());
        soccerField.changeToiletStatus(soccerFieldDto.isToiletStatus());
        soccerField.changeImages(soccerFieldDto.getFieldImg1(), soccerFieldDto.getFieldImg2(), soccerFieldDto.getFieldImg3());
        soccerField.changeInOutWhether(soccerFieldDto.getInOutWhether());
        soccerField.changeGrassWhether(soccerFieldDto.getGrassWhether());

        return soccerField;
    }

    // 구장 객체 삭제
    @Override
    public void delete(long id) {
        soccerFieldRepository.deleteById(id);
    }

    @Override
    public SoccerFieldInfoDTO findById(long id) {
        SoccerField soccerField = soccerFieldRepository.findById(id).get();
        return new SoccerFieldInfoDTO(soccerField);
    }

    // province.json을 String으로 변환
    @Override
    public List<String> getProvinces() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("province.json");
        ObjectMapper objectMapper = new ObjectMapper();
        return Arrays.asList(objectMapper.readValue(inputStream, String[].class));
    }

    // 해당 지역이 존재하는지 확인하고, 존재한다면 해당 지역의 축구장을 반환
    @Override
    public List<SoccerField> searchFieldByProvince(String province) {
        List<String> provinces;
        try {
            provinces = getProvinces();
        } catch (IOException e) {
            throw new RuntimeException("json파일 읽기 실패 ", e);
        }

        if (!provinces.contains(province)) {
            return new ArrayList<>();
        }

        return soccerFieldRepository.findByProvinceContaining(province);
    }
}