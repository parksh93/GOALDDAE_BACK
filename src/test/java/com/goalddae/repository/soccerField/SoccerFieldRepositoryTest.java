package com.goalddae.repository.soccerField;

import com.goalddae.entity.SoccerField;
import com.goalddae.repository.FieldReservationRepository;
import com.goalddae.repository.SoccerFieldRepository;
import com.goalddae.service.MatchService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class SoccerFieldRepositoryTest {

    @Autowired
    SoccerFieldRepository soccerFieldRepository;

    @Autowired
    FieldReservationRepository fieldReservationRepository;

    @Autowired
    MatchService matchService;

    private SoccerField soccerField;

    @Test
    @Transactional
    @DisplayName("구장 객체 생성 테스트")
    public void saveSoccerFieldTest() {
        // 구장 객체 생성 및 저장
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
                .fieldName(updateSoccerFieldName)
                .toiletStatus(true)
                .showerStatus(true)
                .parkingStatus(true)
                .fieldSize("기존 사이즈")
                .fieldImg1("기존 이미지1")
                .fieldImg2("기존 이미지2")
                .fieldImg3("기존 이미지3")
                .reservationFee(4000)
                .inOutWhether("실외")
                .grassWhether("천연")
                .region("용인")
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
