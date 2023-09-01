package com.goalddae.repository.soccerField;

import com.goalddae.entity.SoccerField;
import com.goalddae.repository.SoccerFieldRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class SoccerFieldRepositoryTest {

    @Autowired
    SoccerFieldRepository soccerFieldRepository;

    private SoccerField soccerField;

    @Test
    @Transactional
    @DisplayName("구장 객체 생성 테스트")
    public void saveSoccerFieldTest() {

        // 구장 객체 생성 및 저장
        soccerField = SoccerField.builder()
                .id(1L)
                .fieldName("테스트구장 생성")
                .operatingHours(LocalTime.parse("10:00"))
                .closingTime(LocalTime.parse("21:00"))
                .playerCapacity(10)
                .region("분당")
                .reservationFee(10000)
                .inOutWhether("실내")
                .grassWhether("천연")
                .toiletStatus(true)
                .showerStatus(true)
                .parkingStatus(true)
                .build();

        // 저장 후 반환된 객체 받기
        SoccerField saveSoccerField = soccerFieldRepository.save(soccerField);

        // 저장된 구장 객체 검증
        assertThat(saveSoccerField).isNotNull();
        assertThat(saveSoccerField.getFieldName()).isEqualTo(soccerField.getFieldName());
    }

    @Test
    @Transactional
    @DisplayName("구장 객체 정보 수정 테스트")
    public void updateSoccerFieldTest() {
        // Given: 기존에 저장되어 있는 Soccer Field 정보와 업데이트 할 정보 생성
        Long soccerFiledId = 1L;
        String updateSoccerFieldName = "수정된 축구장A";

        SoccerField soccerField = SoccerField.builder()
                .id(1L)
                .fieldName(updateSoccerFieldName)
                .operatingHours(LocalTime.parse("10:00"))
                .closingTime(LocalTime.parse("21:00"))
                .playerCapacity(10)
                .region("분당")
                .reservationFee(10000)
                .inOutWhether("실내")
                .grassWhether("천연")
                .toiletStatus(true)
                .showerStatus(true)
                .parkingStatus(true)
                .build();

        // When
        SoccerField existingSoccerField = soccerFieldRepository.findById(soccerFiledId).orElse(null);

        if (existingSoccerField != null) {
            SoccerField saveSoccerField = soccerFieldRepository.save(soccerField);

            // Then
            assertThat(saveSoccerField.getFieldName()).isEqualTo(updateSoccerFieldName);
        }
    }

    @Test
    @Transactional
    @DisplayName("구장 객체 삭제 테스트")
    public void deleteSoccerFieldTest() {
        // Given: 새로운 Soccer Field 생성 및 저장
        SoccerField soccerField = SoccerField.builder()
                .id(1L)
                .fieldName("테스트구장 변경 후")
                .operatingHours(LocalTime.parse("10:00"))
                .closingTime(LocalTime.parse("21:00"))
                .playerCapacity(10)
                .region("분당")
                .reservationFee(10000)
                .inOutWhether("실내")
                .grassWhether("천연")
                .toiletStatus(true)
                .showerStatus(true)
                .parkingStatus(true)
                .build();

        soccerField = soccerFieldRepository.save(soccerField);

        // When: 저장된 Soccer Field 삭제
        soccerFieldRepository.deleteById(soccerField.getId());

        // Then: 해당 ID의 SoccerField가 없어졌는지 확인
        Optional<SoccerField> deletedSoccerFiled = soccerFieldRepository.findById(soccerField.getId());

        assertThat(deletedSoccerFiled).isEmpty();
    }

    @Test
    @Transactional
    @DisplayName("구장 정보 가져오기")
    public void findByIdTest(){
        long id = 1;

        SoccerField soccerField = soccerFieldRepository.findById(id).get();

        assertEquals("테스트 구장", soccerField.getFieldName());
    }
}
