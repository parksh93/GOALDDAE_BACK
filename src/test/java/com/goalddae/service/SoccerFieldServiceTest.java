package com.goalddae.service;

<<<<<<< HEAD
import com.goalddae.dto.soccerField.SoccerFieldDTO;
import com.goalddae.entity.SoccerField;
import com.goalddae.repository.SoccerFieldRepository;
=======
import com.goalddae.dto.fieldReservation.FieldReservationInfoDTO;
import com.goalddae.dto.soccerField.SoccerFieldDTO;
import com.goalddae.entity.ReservationField;
import com.goalddae.entity.SoccerField;
import com.goalddae.entity.User;
import com.goalddae.repository.ReservationFieldJPARepository;
import com.goalddae.repository.SoccerFieldRepository;
import com.goalddae.repository.UserJPARepository;
>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
<<<<<<< HEAD

import java.time.LocalTime;
=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc
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

<<<<<<< HEAD
    @MockBean
    private FieldReservationService fieldReservationService;

    @Autowired
    private SoccerFieldService soccerFieldService;

=======
    @Autowired
    private SoccerFieldService soccerFieldService;

    @MockBean
    private UserJPARepository userJPARepository;

    @MockBean
    private ReservationFieldJPARepository reservationFieldJPARepository;

>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc
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
<<<<<<< HEAD
                .playerCapacity(12)
=======
>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc
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
<<<<<<< HEAD
                .playerCapacity(10)
=======
>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc
                .province("경기도")
                .region("분당")
                .reservationFee(10000)
                .inOutWhether("실내")
                .grassWhether("천연")
                .toiletStatus(true)
                .showerStatus(true)
                .parkingStatus(true)
                .build();

<<<<<<< HEAD
                        when(soccerFieldRepository.findById(anyLong())).thenReturn(Optional.of(existing));
        when(soccerFieldRepository.save(any(SoccerField.class))).thenReturn(existing);

        // When: 서비스 메소드 호출
        SoccerField result = soccerFieldService.update(updateDto);

        // Then: 결과 확인
=======
        when(soccerFieldRepository.findById(anyLong())).thenReturn(Optional.of(existing));
        when(soccerFieldRepository.save(any(SoccerField.class))).thenReturn(existing);

        // When
        SoccerField result = soccerFieldService.update(updateDto);

        // Then
>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc
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
<<<<<<< HEAD
=======

    @Test
    @Transactional
    @DisplayName("구장 전체 조회")
    public void getSoccerFieldListTest() {
        List<SoccerField> soccerFieldList = soccerFieldService.getSoccerFieldList();

        assertEquals("테스트 구장", soccerFieldList.get(0).getFieldName());
    }

    @DisplayName("필터를 이용하여 예약구장 조회")
    public void findFieldReservationTest() {
        // Given
        String province = "서울";
        LocalDate reservationDate = LocalDate.of(2023, 9, 14);
        String reservationPeriod = "저녁";
        String inOutWhether = "실내";
        String grassWhether = "천연";

        SoccerField soccerField1 = SoccerField.builder()
                .id(1L)
                .fieldName("테스트 구장1")
                .province("서울")
                .inOutWhether("실내")
                .grassWhether("천연")
                .operatingHours(LocalTime.of(9, 0))
                .closingTime(LocalTime.of(22, 0))
                .build();

        SoccerField soccerField2 = SoccerField.builder()
                .id(2L)
                .fieldName("테스트 구장2")
                .province("서울")
                .inOutWhether("실내")
                .grassWhether("천연")
                .operatingHours(LocalTime.of(9, 0))
                .closingTime(LocalTime.of(22, 0))
                .build();

        List<SoccerField> fields = Arrays.asList(soccerField1, soccerField2);
        Page<SoccerField> pageFields= new PageImpl<>(fields);

        when(soccerFieldRepository.findById(anyLong())).thenReturn(Optional.of(soccerField1));
        when(soccerFieldRepository.findAllByProvince(anyString(), any(PageRequest.class))).thenReturn(pageFields);
        when(soccerFieldRepository.findByProvinceAndGrassWhether(anyString(), anyString(), any(PageRequest.class))).thenReturn(pageFields);
        when(soccerFieldRepository.findByProvinceAndInOutWhether(anyString(), anyString(), any(PageRequest.class))).thenReturn(pageFields);
        when(soccerFieldRepository.findByProvinceAndInOutWhetherAndGrassWhether(anyString(), anyString(),anyString() ,any(PageRequest.class))).thenReturn(pageFields);

        // When
        Page<SoccerFieldDTO> resultPage =
                soccerFieldService.findAvailableField(Optional.empty(),
                        province,
                        inOutWhether,
                        grassWhether,
                        reservationDate,
                        reservationPeriod,
                        1,
                        fields.size());

        // Then
        assertFalse(resultPage.isEmpty());
        assertEquals(fields.size(), resultPage.getTotalElements());
    }



    @Test
    @Transactional
    @DisplayName("특정 날짜에 해당 구장에서 이미 예약된 시간과 예약 가능 시간 조회")
    public void findAvailableFieldReservationTest() {
        // Given
        Long fieldId = 1L;
        LocalDate date = LocalDate.of(2023, 9, 12);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(22, 0);

        SoccerField soccerField = SoccerField.builder()
                .id(fieldId)
                .fieldName("테스트 구장1")
                .operatingHours(LocalTime.of(9,0))
                .closingTime(LocalTime.of(22,0))
                .build();

        ReservationField reservation1 = ReservationField.builder()
                .soccerField(soccerField)
                .startDate(date.atStartOfDay().plusHours(10))
                .endDate(date.atStartOfDay().plusHours(12))
                .build();

        ReservationField reservation2 = ReservationField.builder()
                .soccerField(soccerField)
                .startDate(date.atStartOfDay().plusHours(15))
                .endDate(date.atStartOfDay().plusHours(17))
                .build();

        List<ReservationField> reservations = Arrays.asList(reservation1, reservation2);

        when(soccerFieldRepository.findById(fieldId)).thenReturn(Optional.of(soccerField));
        when(reservationFieldJPARepository.findBySoccerFieldIdAndReservedDateBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(reservations);

        // When
        FieldReservationInfoDTO resultInfoDTO = soccerFieldService.getReservationInfo(fieldId, date, startTime, endTime);

        // Then
        assertNotNull(resultInfoDTO);
        assertEquals(Arrays.asList(
                LocalTime.of(9,0),
                LocalTime.of(12,0),
                LocalTime.of(13,0),
                LocalTime.of(14,0),
                LocalTime.of(17,0),
                LocalTime.of(18,0),
                LocalTime.of (19 ,00 ),
                LocalTime.of (20 ,00 ),
                LocalTime.of (21 ,00 )
        ), resultInfoDTO.getAvailableTimes());
    }
>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc
}