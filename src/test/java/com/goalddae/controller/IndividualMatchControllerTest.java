package com.goalddae.controller;

import com.goalddae.dto.match.CancelMatchRequestDTO;
import com.goalddae.dto.match.IndividualMatchDTO;
import com.goalddae.dto.match.IndividualMatchRequestDTO;
import com.goalddae.dto.match.SaveIndividualMatchDTO;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IndividualMatchControllerTest {

//    @MockBean
//    private IndividualMatchService individualMatchService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    @DisplayName("개인매치 조회 컨트롤러 테스트")
//    public void findIndividualMatchControllerTest() throws Exception {
//
//        when(individualMatchService.getMatchesByDateAndProvinceAndLevelAndGender(any(), any(), any(), any()))
//                .thenReturn(Collections.singletonList(new IndividualMatchDTO()));
//
//        mockMvc.perform(get("/match/individual")
//                        .param("startTime", "2023-09-05T14:00:00")
//                        .param("province", "서울")
//                        .param("level", "유망주")
//                        .param("gender", "남자"))
//                .andExpect(status().isOk());
//
//        verify(individualMatchService, times(1)).getMatchesByDateAndProvinceAndLevelAndGender(any(), any(), any(), any());
//    }

//    @Test
//    @DisplayName("개인매치 신청내역 조회 테스트")
//    public void getIndividualMatchesRequestTest() throws Exception {
//
//        long userId = 1;
//
//        // IndividualMatch 객체 생성
//        IndividualMatch individualMatch = new IndividualMatch();
//        // IndividualMatchRequest 객체 생성
//        IndividualMatchRequest matchRequest = IndividualMatchRequest.builder()
//                .individualMatch(individualMatch)
//                .build();
//        // IndividualMatchRequest 리스트 생성 후 리턴
//        List<IndividualMatchRequest> matchRequests = Collections.singletonList(matchRequest);
//        when(individualMatchService.findAllByUserId(userId)).thenReturn(matchRequests);
//
//        // IndividualMatchRequestDTO 생성
//        IndividualMatchRequestDTO expectedDto = IndividualMatchRequestDTO.builder()
//                .id(matchRequests.get(0).getIndividualMatch().getId())
//                .playerNumber(matchRequests.get(0).getIndividualMatch().getPlayerNumber())
//                .level(matchRequests.get(0).getIndividualMatch().getLevel())
//                .gender(matchRequests.get(0).getIndividualMatch().getGender())
////                .reservationField(matchRequests.get(0).getIndividualMatch().getReservationField())
//                .startTime(matchRequests.get(0).getIndividualMatch().getStartTime())
//                .endTime(matchRequests.get(0).getIndividualMatch().getEndTime())
//                .build();
//
//        //
//        mockMvc.perform(get("/match/my-individual/" + userId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(expectedDto.getId()))
//                .andExpect(jsonPath("$[0].gender").value(expectedDto.getGender()))
//                .andExpect(jsonPath("$[0].playerNumber").value(expectedDto.getPlayerNumber()))
//                .andExpect(jsonPath("$[0].level").value(expectedDto.getLevel()));
////                .andExpect(jsonPath("$[0].reservationField").value(expectedDto.getReservationField()));
//    }

    @Test
    @Transactional
    @DisplayName("개인 매치 상세정보 조회")
    public void getIndividualMatchDetailTest() throws Exception {
        long matchId = 1;
        String url = "/match/individual/detail/" + matchId;

        ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value("안녕"));
    }

    @Test
    @Transactional
    @DisplayName("개인 매치 참가 인원 조회")
    public void getIndividualMatchPlayerTest() throws Exception {
        long matchId = 1;
        String url = "/match/individual/getPlayer/" + matchId;

        ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickname").value("뭐요"));
    }

    @Test
    @Transactional
    @DisplayName("개인 매치 참가 신청")
    public void saveIndividualMatchRequestTest() throws Exception {
        long matchId = 1;
        long userId = 20;
        String url1 = "/match/individual/request";

        SaveIndividualMatchDTO saveIndividualMatchDTO =SaveIndividualMatchDTO.builder()
                .matchId(matchId)
                .userId(userId)
                .build();

        final String request = objectMapper.writeValueAsString(saveIndividualMatchDTO);

        mockMvc.perform(put(url1).contentType(MediaType.APPLICATION_JSON).content(request));

        String url2 = "/match/individual/getPlayer/" + matchId;

        ResultActions resultActions = mockMvc.perform(get(url2).accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickname").value("뭐요"));
    }

    @Test
    @Transactional
    @DisplayName("매치 참가 취소")
    public void cancelMatchRequestTest() throws Exception {
        long userId = 20;
        long matchId = 1;
        CancelMatchRequestDTO cancelMatchRequestDTO = CancelMatchRequestDTO.builder()
                .userId(userId)
                .matchId(matchId)
                .build();

        String url1 = "/match/individual/cancelRequest";
        final String request = objectMapper.writeValueAsString(cancelMatchRequestDTO);

        mockMvc.perform(delete(url1).content(request).contentType(MediaType.APPLICATION_JSON));

        String url2 = "/match/individual/getPlayer/" + matchId;

        ResultActions resultActions = mockMvc.perform(get(url2).accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

    }

}
