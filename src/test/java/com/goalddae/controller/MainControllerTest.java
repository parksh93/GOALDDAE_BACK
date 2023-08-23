package com.goalddae.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.entity.SoccerField;
import com.goalddae.service.SoccerFieldService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private SoccerFieldService soccerFieldService;

    @Test
    @Transactional
    @DisplayName("검색 조건에 맞는 축구장을 찾을 수 있는지 테스트")
    public void searchFieldsTest() throws Exception {
        // Field 객체 생성
        SoccerField sampleField = SoccerField.builder()
                .id(1L)
                .region("성남")
                .fieldName("모란 풋살장")
                .build();

        when(soccerFieldService.searchSoccerFields(any(String.class)))
                .thenReturn(Arrays.asList(sampleField));

        mockMvc.perform(get("/search/soccerField")
                        .param("region", "성남")
                        .param("fieldName", "모란 풋살장"))
                .andExpect(status().isOk())
                .andExpect(result -> print());
    }


    @Test
    @DisplayName("구장 등록 및 테이블 생성 확인")
    void addSoccerFieldTest() throws Exception {
        SoccerField soccerField = SoccerField.builder()
                .fieldName("테스트 풋살장")
                .toiletStatus(true)
                .showerStatus(true)
                .parkingStatus(true)
                .fieldSize("14x16")
                .fieldImg1("테스트이미지1")
                .inOutWhether("실외")
                .grassWhether("인조")
                .region("서울")
                .build();

        // 구장 등록
        mockMvc.perform(post("/addSoccerField")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(soccerField)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("fieldName", is(soccerField.getFieldName())));
    }
}
