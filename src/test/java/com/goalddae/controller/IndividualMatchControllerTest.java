package com.goalddae.controller;

import com.goalddae.dto.match.IndividualMatchDTO;
import com.goalddae.dto.match.IndividualMatchRequestDTO;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.entity.IndividualMatchRequest;
import com.goalddae.entity.ReservationField;
import com.goalddae.service.IndividualMatchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IndividualMatchControllerTest {

    @MockBean
    private IndividualMatchService individualMatchService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("개인매치 조회 컨트롤러 테스트")
    public void findIndividualMatchControllerTest() throws Exception {

        when(individualMatchService.getMatchesByDateAndProvinceAndLevelAndGender(any(), any(), any(), any()))
                .thenReturn(Collections.singletonList(new IndividualMatchDTO()));

        mockMvc.perform(get("/match/individual")
                        .param("startTime", "2023-09-05T14:00:00")
                        .param("province", "서울")
                        .param("level", "유망주")
                        .param("gender", "남자"))
                .andExpect(status().isOk());

        verify(individualMatchService, times(1)).getMatchesByDateAndProvinceAndLevelAndGender(any(), any(), any(), any());
    }

    @Test
    @DisplayName("개인매치 신청내역 조회 테스트")
    public void getIndividualMatchesRequestTest() throws Exception {

        long userId = 1;

        // IndividualMatch 객체 생성
        IndividualMatch individualMatch = new IndividualMatch();
        // IndividualMatchRequest 객체 생성
        IndividualMatchRequest matchRequest = IndividualMatchRequest.builder()
                .individualMatch(individualMatch)
                .build();
        // IndividualMatchRequest 리스트 생성 후 리턴
        List<IndividualMatchRequest> matchRequests = Collections.singletonList(matchRequest);
        when(service.findAllByUserId(userId)).thenReturn(matchRequests);

        // IndividualMatchRequestDTO 생성
        IndividualMatchRequestDTO expectedDto = IndividualMatchRequestDTO.builder()
                .id(matchRequests.get(0).getIndividualMatch().getId())
                .playerNumber(matchRequests.get(0).getIndividualMatch().getPlayerNumber())
                .level(matchRequests.get(0).getIndividualMatch().getLevel())
                .gender(matchRequests.get(0).getIndividualMatch().getGender())
//                .reservationField(matchRequests.get(0).getIndividualMatch().getReservationField())
                .startTime(matchRequests.get(0).getIndividualMatch().getStartTime())
                .endTime(matchRequests.get(0).getIndividualMatch().getEndTime())
                .build();

        //
        mockMvc.perform(get("/match/my-individual/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(expectedDto.getId()))
                .andExpect(jsonPath("$[0].gender").value(expectedDto.getGender()))
                .andExpect(jsonPath("$[0].playerNumber").value(expectedDto.getPlayerNumber()))
                .andExpect(jsonPath("$[0].level").value(expectedDto.getLevel()));
//                .andExpect(jsonPath("$[0].reservationField").value(expectedDto.getReservationField()));
    }

}
