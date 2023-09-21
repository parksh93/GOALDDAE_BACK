package com.goalddae.service;

<<<<<<< HEAD
import com.goalddae.dto.match.IndividualMatchDTO;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.entity.IndividualMatchRequest;
import com.goalddae.entity.ReservationField;
import com.goalddae.entity.SoccerField;
=======
import com.goalddae.dto.match.*;
import com.goalddae.entity.*;
>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc
import com.goalddae.repository.IndividualMatchJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
<<<<<<< HEAD
=======
import org.springframework.transaction.annotation.Transactional;
>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
<<<<<<< HEAD
=======
import static org.assertj.core.api.AssertionsForClassTypes.in;
>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
<<<<<<< HEAD
@AutoConfigureMockMvc
=======
//@AutoConfigureMockMvc
>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc
public class IndividualMatchServiceTest {

    @Autowired
    private IndividualMatchService individualMatchService;

<<<<<<< HEAD
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
=======
//    @MockBean
//    private IndividualMatchJPARepository individualmatchJPARepository;

//    @Test
//    @DisplayName("개인매치 조회 서비스 테스트")
//    public void findIndividualMatchServiceTest() {
//        // Given
//        SoccerField mockSoccerField = mock(SoccerField.class);
//        when(mockSoccerField.getFieldName()).thenReturn("테스트 구장");
//
//        ReservationField mockField = mock(ReservationField.class);
//        when(mockField.getSoccerField()).thenReturn(mockSoccerField);
//
//        LocalDate date = LocalDate.now();
//        String province = "경기";
//        String level = "유망주";
//        String gender = "남자";
//
//        List<IndividualMatchRequest> requests = new ArrayList<>();
//
//        IndividualMatch individualMatch = IndividualMatch.builder()
//                .id(1L)
//                .startTime(LocalDateTime.parse("2023-09-05T14:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
//                .endTime(LocalDateTime.parse("2023-09-05T16:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
//                .playerNumber(12)
//                .gender("남자")
//                .level("유망주")
//                .status("신청가능")
//                .reservationField(mockField)
//                .requests(requests)
//                .build();
//
//        when(individualmatchJPARepository.findMatches(any(LocalDateTime.class), any(LocalDateTime.class), eq(province), eq(level), eq(gender)))
//                .thenReturn(Collections.singletonList(individualMatch));
//
//        // When
//        List<IndividualMatchDTO> result = individualMatchService.getMatchesByDateAndProvinceAndLevelAndGender(date, province, level, gender);
//
//        // Then
//        assertThat(result).isNotEmpty();
//        verify(individualmatchJPARepository, times(1)).findMatches(any(LocalDateTime.class), any(LocalDateTime.class), eq(province), eq(level), eq(gender));
//    }
>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc

    @Test
    @DisplayName("개인매치 신청내역 조회 테스트")
    public void findAllByUserIdTest() {
        // given
<<<<<<< HEAD
        long userId = 1;
=======
        long userId = 19;
>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc

        // when
        List<IndividualMatchRequest> individualMatchRequestList = individualMatchService.findAllByUserId(userId);

        // then
        assertEquals("남자", individualMatchRequestList.get(0).getIndividualMatch().getGender());
        assertEquals(1, individualMatchRequestList.get(0).getIndividualMatch().getReservationField().getSoccerField().getId());
    }
<<<<<<< HEAD
=======

    @Test
    @Transactional
    @DisplayName("개인매치 상세정보 조회")
    public void findByIdTest(){
        long matchId = 1;

        IndividualMatchDetailDTO individualMatchDetailDTO = individualMatchService.findById(matchId);

        assertEquals("뻥차풋살장", individualMatchDetailDTO.getFieldName());
        assertEquals("안녕", individualMatchDetailDTO.getNickname());
    }

    @Test
    @Transactional
    @DisplayName("개인 매치 참가 신청")
    public void saveMatchRequestTest() {
        SaveIndividualMatchDTO saveIndividualMatchDTO = SaveIndividualMatchDTO.builder()
                .matchId(1)
                .userId(20)
                .build();
        individualMatchService.saveMatchRequest(saveIndividualMatchDTO);

        List<GetPlayerInfoDTO> userList = individualMatchService.getMatchPlayerInfo(1);

        assertEquals(1, userList.size());
        assertEquals("뭐요", userList.get(0).getNickname());
    }

    @Test
    @Transactional
    @DisplayName("매치 참가 인원 조회")
    public void getMatchPlayerInfo() {
        long matchId = 1;

        List<GetPlayerInfoDTO> playerList = individualMatchService.getMatchPlayerInfo(matchId);

        assertEquals(1, playerList.size());
    }

    @Test
    @Transactional
    @DisplayName("매치 참가 취소")
    public void cancelMatchRequest() {
        long matchId = 1;
        long userId = 20;
        CancelMatchRequestDTO cancelMatchRequestDTO = CancelMatchRequestDTO.builder()
                .matchId(matchId)
                .userId(userId)
                .build();

        individualMatchService.cancelMatchRequest(cancelMatchRequestDTO);

        List<GetPlayerInfoDTO> playerList = individualMatchService.getMatchPlayerInfo(matchId);

        assertEquals(0, playerList.size());
    }

>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc
}