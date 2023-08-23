package com.goalddae.repository.soccerField;

import com.goalddae.entity.SoccerField;
import com.goalddae.service.FieldReservationService;
import com.goalddae.service.MatchService;
import com.goalddae.service.SoccerFieldService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(SoccerFieldService.class)
public class SoccerFieldRepositoryTest {

    @Autowired
    private SoccerFieldService soccerFieldService;

    @MockBean
    private FieldReservationService fieldReservationService;

    @MockBean
    private MatchService matchService;

    private SoccerField soccerField;

    @BeforeEach
    public void createSoccerFieldTest() {
        // 구장 객체 생성
        soccerField = SoccerField.builder()
                .fieldName("테스트 구장")
                .toiletStatus(true)
                .showerStatus(true)
                .parkingStatus(true)
                .fieldSize("5인용")
                .fieldImg1("이미지1.png")
                .reservationFee(10000)
                .inOutWhether("실내")
                .grassWhether("잔디")
                .region("서울")
                .build();
    }

    @Test
    @DisplayName("구장 동적 테이블 생성 테스트")
    public void dynamicTableTest() {
        // 생성한 구장 객체를 저장하고 동적 테이블 생성 메서드들을 호출
//        when(fieldReservationService.createFieldReservationTable(any(String.class))).thenReturn(true);
//        when(matchService.createMatchIndividualTable(any(String.class))).thenReturn(true);
//        when(matchService.createMatchTeamTable(any(String.class))).thenReturn(true);
//
//        SoccerField newSoccerField = soccerFieldService.addSoccerField(soccerField);
//
//        // 동적 테이블 생성 확인
//        assertThat(fieldReservationService.createFieldReservationTable(newSoccerField.getFieldName())).isTrue();
//        assertThat(matchService.createMatchIndividualTable(newSoccerField.getFieldName())).isTrue();
//        assertThat(matchService.createMatchTeamTable(newSoccerField.getFieldName())).isTrue();
    }

    @AfterEach
    public void dropSoccerFieldTest() {
        // 테스트 후 해당 구장을 삭제
        SoccerField foundSoccerField = soccerFieldService.findSoccerFieldByName(soccerField.getFieldName());
        if (foundSoccerField != null) {
            soccerFieldService.deleteSoccerField(foundSoccerField.getId());
        }
    }
}