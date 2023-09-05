package com.goalddae.controller;

import com.goalddae.dto.match.IndividualMatchDTO;
import com.goalddae.service.IndividualMatchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IndividualMatchControllerTest {

    @MockBean
    private IndividualMatchService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("개인매치 조회 컨트롤러 테스트")
    public void findIndividualMatchControllerTest() throws Exception {

        when(service.getMatchesByDateAndProvinceAndLevelAndGender(any(), any(), any(), any()))
                .thenReturn(Collections.singletonList(new IndividualMatchDTO()));

        mockMvc.perform(get("/match/individual")
                        .param("startTime", "2023-09-05T14:00:00")
                        .param("province", "서울")
                        .param("level", "유망주")
                        .param("gender", "남자"))
                .andExpect(status().isOk());

        verify(service, times(1)).getMatchesByDateAndProvinceAndLevelAndGender(any(), any(), any(), any());
    }
}
