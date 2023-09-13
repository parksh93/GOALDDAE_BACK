package com.goalddae.service;

import com.goalddae.dto.fieldReservation.FieldReservationInfoDTO;
import com.goalddae.dto.soccerField.SoccerFieldDTO;
import com.goalddae.entity.ReservationField;
import com.goalddae.entity.SoccerField;
import com.goalddae.entity.User;
import com.goalddae.repository.ReservationFieldJPARepository;
import com.goalddae.repository.SoccerFieldRepository;
import com.goalddae.repository.UserJPARepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
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

    @Autowired
    private SoccerFieldService soccerFieldService;

    @MockBean
    private UserJPARepository userJPARepository;

    @MockBean
    private ReservationFieldJPARepository reservationFieldJPARepository;

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

    @Test
    @Transactional
    @DisplayName("필터를 이용하여 예약구장 조회")
    public void findFieldReservationTest() {
        // Given
        Long userId = 1L;
        LocalTime operatingHours = LocalTime.of(9, 0);
        LocalTime closingTime = LocalTime.of(22, 0);
        String inOutWhether = "실내";
        String grassWhether = "천연";

        User user = User.builder()
                .id(userId)
                .preferredCity("서울")
                .build();

        SoccerField soccerField1 = SoccerField.builder()
                .id(1L)
                .fieldName("테스트 구장1")
                .region("서울")
                .inOutWhether("실내")
                .grassWhether("천연")
                .build();

        SoccerField soccerField2 = SoccerField.builder()
                .id(2L)
                .fieldName("테스트 구장2")
                .inOutWhether("실외")
                .grassWhether("인조")
                .build();

        List<SoccerField> fields = Arrays.asList(soccerField1, soccerField2);

        when(userJPARepository.findById(userId)).thenReturn(Optional.of(user));
        when(soccerFieldRepository.findAvailableField(anyString(), any(LocalTime.class), any(LocalTime.class),
                                                        anyString(), anyString())).thenReturn(fields);

        // When
        List<SoccerFieldDTO> resultFields =
                soccerFieldService.findAvailableField(Optional.ofNullable(userId), operatingHours,
                        closingTime, inOutWhether,
                        grassWhether);

        // Then
        assertNotNull(resultFields);
        assertEquals(fields.size(), resultFields.size());
    }


    @Test
    @Transactional
    @DisplayName("특정 날짜에 해당 구장에서 이미 예약된 시간과 예약 가능 시간 조회")
    public void findAvailableFieldReservationTest() {
        // Given
        Long fieldId = 1L;
        LocalDate date = LocalDate.of(2023, 9, 12);

        SoccerField soccerField = SoccerField.builder()
                .id(fieldId)
                .fieldName("테스트 구장1")
                .operatingHours(LocalTime.of(9,0))
                .closingTime(LocalTime.of(22,0))
                .build();

        ReservationField reservation1 = ReservationField.builder()
                .soccerField(soccerField)
                .reservedDate(date.atStartOfDay().plusHours(8)) // 예약 : 8시
                .startDate(date.atStartOfDay().plusHours(10)) // 경기 시작: 10시
                .endDate(date.atStartOfDay().plusHours(12)) // 경기 종료: 12시
                .build();

        ReservationField reservation2 = ReservationField.builder()
                .soccerField(soccerField)
                .reservedDate(date.atStartOfDay().plusHours(13)) // 예약 : 13시
                .startDate(date.atStartOfDay().plusHours(15)) // 경기 시작: 15시
                .endDate(date.atStartOfDay().plusHours(17)) // 경기 종료: 17시
                .build();

        List<ReservationField> reservations = Arrays.asList(reservation1, reservation2);

        when(soccerFieldRepository.findById(fieldId)).thenReturn(Optional.of(soccerField));
        when(reservationFieldJPARepository.findBySoccerFieldIdAndReservedDateBetween(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(reservations);

        // When
        FieldReservationInfoDTO resultInfoDTO = soccerFieldService.getReservationInfo(fieldId,date);

        // Then
        assertNotNull(resultInfoDTO);
        assertEquals(Arrays.asList(
                LocalTime.of(9,0),
                LocalTime.of(13,0),
                LocalTime.of(14,0),
                LocalTime.of(18,0),
                LocalTime.of(19,0),
                LocalTime.of (20 ,00 ) ,
                LocalTime. of (21 ,00 ),
                LocalTime. of (22 ,00 )
        ), resultInfoDTO.getAvailableTimes());
    }
}