package com.goalddae.service;

import com.goalddae.dto.fieldReservation.FieldReservationInfoDTO;
import com.goalddae.dto.soccerField.SoccerFieldDTO;
import com.goalddae.dto.soccerField.SoccerFieldInfoDTO;
import com.goalddae.entity.SoccerField;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface SoccerFieldService {
    // 검색 기능
    List<SoccerField> searchSoccerFields(String searchTerm);
    // 지역 조회
    List<String> searchCityNames(String searchTerm) throws IOException;
    SoccerField save(SoccerField soccerField);
    SoccerField update(SoccerFieldDTO soccerFieldDTO);
    SoccerField findSoccerFieldByName(String fieldName);
    void delete(long id);
    public SoccerFieldInfoDTO findById(long id);
    String uploadImage (MultipartFile multipartFile);
    List<SoccerField> getSoccerFieldList();

    // 필터를 이용한 예약구장리스트 조회
    Page<SoccerFieldDTO> findAvailableField(Optional<Long> userId,
                                            String province,
                                            String inOutWhether,
                                            String grassWhether,
                                            LocalDate reservationDate,
                                            String reservationPeriod,
                                            int pageNumber,
                                            int pageSize);

    // 특정 날짜에 대해 해당 구장에서 이미 예약된 시간과 아직 예약 가능한 시간을 조회
    FieldReservationInfoDTO getReservationInfo(Long fieldId, LocalDate date, LocalTime startTime, LocalTime endTime);
}