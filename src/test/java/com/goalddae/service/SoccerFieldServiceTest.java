package com.goalddae.service;

import com.goalddae.dto.soccerField.SoccerFieldDTO;
import com.goalddae.entity.SoccerField;
import com.goalddae.repository.SoccerFieldRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SoccerFieldServiceTest {

    @MockBean
    private SoccerFieldRepository soccerFieldRepository;

    @MockBean
    private FieldReservationService fieldReservationService;

    @Autowired
    private SoccerFieldService soccerFieldService;

    private SoccerField soccerField;

    @Test
    @Transactional
    @DisplayName("주어진 지역에 따라 축구장 목록 검색")
    public void searchFieldsByRegionTest() {
        String region = "성남";

        List<SoccerField> soccerFields = soccerFieldService.searchSoccerFields(region);

        // 검색된 축구장 목록에 해당 지역이 포함되어 있는지 확인
        assertTrue(soccerFields.stream().anyMatch(soccerField -> soccerField.getRegion().equals(region)));
    }

    @Test
    @Transactional
    @DisplayName("주어진 축구장 이름으로 검색")
    public void searchFieldsByFieldNameTest() {
        String fieldName = "테스트 구장8";

        List<SoccerField> soccerFields = soccerFieldService.searchSoccerFields(fieldName);

        // 검색된 축구장 목록에 해당 축구장 이름이 포함되어 있는지 확인
        assertTrue(soccerFields.stream().anyMatch(soccerField -> soccerField.getFieldName().equals(fieldName)));
    }

    @Test
    @Transactional
    @DisplayName("구장 객체 생성 테스트")
    public void saveSoccerFieldTest(){
        // Given
        soccerField = SoccerField.builder()
                .fieldName("테스트구장")
                .toiletStatus(true)
                .showerStatus(true)
                .parkingStatus(true)
                .fieldSize("14x16")
                .fieldImg1("이미지1")
                .reservationFee(8000)
                .inOutWhether("실내")
                .grassWhether("천연")
                .region("인천")
                .province("인천")
                .build();

        when(soccerFieldRepository.save(any(SoccerField.class))).thenReturn(soccerField);

        // when
        SoccerField saveSoccerField = soccerFieldService.save(soccerField);

        assertThat(saveSoccerField).isNotNull();
        assertThat(saveSoccerField.getFieldName()).isEqualTo(soccerField.getFieldName());
    }

    @Test
    @DisplayName("구장 정보 수정 테스트")
    public void updateSoccerFields() {
        // Given: 기존에 저장되어 있는 Soccer Field 정보와 업데이트 할 정보 생성
        SoccerField existing = SoccerField.builder()
                .id(1L)
                .fieldName("테스트구장 수정 전")
                .operatingHours(LocalTime.parse("09:00"))
                .closingTime(LocalTime.parse("20:00"))
                .playerCapacity(12)
                .province("경기도")
                .region("성남")
                .reservationFee(8000)
                .inOutWhether("실외")
                .grassWhether("인조")
                .toiletStatus(true)
                .showerStatus(true)
                .parkingStatus(true)
                .build();

        SoccerFieldDTO updateDto =  SoccerFieldDTO.builder()
                .id(1L)
                .fieldName("테스트구장 변경 후")
                .operatingHours(LocalTime.parse("10:00"))
                .closingTime(LocalTime.parse("21:00"))
                .playerCapacity(10)
                .province("경기도")
                .region("분당")
                .reservationFee(10000)
                .inOutWhether("실내")
                .grassWhether("천연")
                .toiletStatus(true)
                .showerStatus(true)
                .parkingStatus(true)
                .build();

                        when(soccerFieldRepository.findById(anyLong())).thenReturn(Optional.of(existing));
        when(soccerFieldRepository.save(any(SoccerField.class))).thenReturn(existing);

        // When: 서비스 메소드 호출
        SoccerField result = soccerFieldService.update(updateDto);

        // Then: 결과 확인
        assertThat(result.getFieldName()).isEqualTo(updateDto.getFieldName());
        assertThat(result.isToiletStatus()).isEqualTo(updateDto.isToiletStatus());
    }

    @Test
    @DisplayName("구장 객체 삭제 테스트")
    public void deleteSoccerFieldTest() {
        // Given
        Long id = 1L;
        doNothing().when(soccerFieldRepository).deleteById(id);

        // When
        soccerFieldService.delete(id);

        // Then
        verify(soccerFieldRepository, times(1)).deleteById(id);
    }
}