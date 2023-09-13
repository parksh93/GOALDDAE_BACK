package com.goalddae.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.dto.fieldReservation.FieldReservationInfoDTO;
import com.goalddae.dto.soccerField.SoccerFieldDTO;
import com.goalddae.dto.soccerField.SoccerFieldInfoDTO;
import com.goalddae.entity.ReservationField;
import com.goalddae.entity.SoccerField;
import com.goalddae.entity.User;
import com.goalddae.repository.ReservationFieldJPARepository;
import com.goalddae.repository.SoccerFieldRepository;
import com.goalddae.repository.UserJPARepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SoccerFieldServiceImpl implements SoccerFieldService {

    private final SoccerFieldRepository soccerFieldRepository;

    private final UserJPARepository userJPARepository;

    private final ReservationFieldJPARepository reservationFieldJPARepository;

    public SoccerFieldServiceImpl(SoccerFieldRepository soccerFieldRepository,
                                  UserJPARepository userJPARepository,
                                  ReservationFieldJPARepository reservationFieldJPARepository) {
        this.soccerFieldRepository = soccerFieldRepository;
        this.userJPARepository = userJPARepository;
        this.reservationFieldJPARepository = reservationFieldJPARepository;
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

    // 필터를 이용한 예약구장리스트 조회
    @Override
    public List<SoccerFieldDTO> findAvailableField(Optional<Long> userId,
                                                   LocalTime operatingHours,
                                                   LocalTime closingTime,
                                                   String inOutWhether,
                                                   String grassWhether) {

        String preferredCity = "서울"; // default city

        if (userId.isPresent()) {
            User user = userJPARepository.findById(userId.get())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId.get()));
            if (user.getPreferredCity() != null && !user.getPreferredCity().isEmpty()) {
                preferredCity = user.getPreferredCity();
            }
        }

        List<SoccerField> field = soccerFieldRepository.findAvailableField(preferredCity, operatingHours,
                                                                            closingTime, inOutWhether,
                                                                            grassWhether);

        List<SoccerFieldDTO> fieldDTO = field.stream()
                .map(fields -> SoccerFieldDTO.builder()
                        .id(fields.getId())
                        .fieldName(fields.getFieldName())
                        .operatingHours(fields.getOperatingHours())
                        .closingTime(fields.getClosingTime())
                        .region(fields.getRegion())
                        .reservationFee(fields.getReservationFee())
                        .fieldSize(fields.getFieldSize())
                        .inOutWhether(fields.getInOutWhether())
                        .grassWhether(fields.getGrassWhether())
                        .toiletStatus(fields.isToiletStatus())
                        .showerStatus(fields.isShowerStatus())
                        .parkingStatus(fields.isParkingStatus())
                        .fieldImg1(fields.getFieldImg1())
                        .fieldImg2(fields.getFieldImg2())
                        .fieldImg3(fields.getFieldImg3())
                        .build()

                )
                .collect(Collectors.toList());

        return fieldDTO;
    }


    // 특정 날짜에 대해 해당 구장에서 예약 가능한 시간 조회
    @Override
    public FieldReservationInfoDTO getReservationInfo(Long fieldId, LocalDate date) {
        SoccerField soccerField = soccerFieldRepository.findById(fieldId)
                .orElseThrow(() -> new EntityNotFoundException("SoccerField not found with id: " + fieldId));

        // 운영 시작 및 종료시각
        LocalTime operatingStart = soccerField.getOperatingHours();
        LocalTime operatingEnd = soccerField.getClosingTime();

        // 전체 운영시각 리스트 생성
        List<LocalTime> allTimes = new ArrayList<>();
        for (LocalTime time = operatingStart; !time.isAfter(operatingEnd); time = time.plusHours(1)) {
            allTimes.add(time);
        }

        // 해당 날짜의 예약된 구장 조회
        List<ReservationField> reservations =
                reservationFieldJPARepository.findBySoccerFieldIdAndReservedDateBetween(
                        fieldId,
                        date.atStartOfDay(),
                        date.plusDays(1).atStartOfDay());

        // 이미 예약된 시각들 리스트 생성
        List<LocalTime> reservedTimes = reservations.stream()
                .flatMap(reservation ->
                        Stream.iterate(reservation.getStartDate().toLocalTime(),
                                time -> !time.isAfter(reservation.getEndDate().toLocalTime()),
                                time -> time.plusHours(1)))
                .collect(Collectors.toList());

        // 전체 운영시각에서 이미 예약된 것들 제거하여 예약 가능한 시간 목록 생성
        List<LocalTime> availableTimes = new ArrayList<>(allTimes);
        availableTimes.removeAll(reservedTimes);

        FieldReservationInfoDTO infoDTO = new FieldReservationInfoDTO();
        infoDTO.setAvailableTimes(availableTimes);
        infoDTO.setReservedTimes(reservedTimes);

        return infoDTO;
    }
}