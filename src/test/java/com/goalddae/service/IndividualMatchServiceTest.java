package com.goalddae.service;

import com.goalddae.dto.match.IndividualMatchDTO;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.entity.IndividualMatchRequest;
import com.goalddae.entity.ReservationField;
import com.goalddae.entity.SoccerField;
import com.goalddae.repository.IndividualMatchJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IndividualMatchServiceTest {

    @Autowired
    private IndividualMatchService individualMatchService;

    @MockBean
    private IndividualMatchJPARepository individualmatchJPARepository;

    @Test
    @DisplayName("개인매치 조회 서비스 테스트")
    public void findIndividualMatchServiceTest() {
        // Given
        SoccerField mockSoccerField = mock(SoccerField.class);
        when(mockSoccerField.getFieldName()).thenReturn("테스트 구장");

        ReservationField mockField = mock(ReservationField.class);
        when(mockField.getSoccerField()).thenReturn(mockSoccerField);

        LocalDate date = LocalDate.now();
        String province = "경기";
        String level = "유망주";
        String gender = "남자";

        List<IndividualMatchRequest> requests = new ArrayList<>();

        IndividualMatch individualMatch = IndividualMatch.builder()
                .id(1L)
                .startTime(LocalDateTime.parse("2023-09-05T14:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .endTime(LocalDateTime.parse("2023-09-05T16:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .playerNumber(12)
                .gender("남자")
                .level("유망주")
                .status("신청가능")
                .reservationField(mockField)
                .requests(requests)
                .build();

        when(individualmatchJPARepository.findMatches(any(LocalDateTime.class), any(LocalDateTime.class), eq(province), eq(level), eq(gender)))
                .thenReturn(Collections.singletonList(individualMatch));

        // When
        List<IndividualMatchDTO> result = individualMatchService.getMatchesByDateAndProvinceAndLevelAndGender(date, province, level, gender);

        // Then
        assertThat(result).isNotEmpty();
        verify(individualmatchJPARepository, times(1)).findMatches(any(LocalDateTime.class), any(LocalDateTime.class), eq(province), eq(level), eq(gender));
    }
}