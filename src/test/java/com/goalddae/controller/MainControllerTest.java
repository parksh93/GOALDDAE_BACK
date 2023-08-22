package com.goalddae.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.entity.SoccerField;
import com.goalddae.repository.SoccerFieldRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SoccerFieldService soccerFieldService;

    @Autowired
    private SoccerFieldRepository soccerFieldRepository;

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
    @Transactional
    @DisplayName("축구장 등록 테스트")
    public void addSoccerFieldTest() throws Exception {
        SoccerField soccerField = SoccerField.builder()
                .region("성남")
                .fieldName("모란 풋살장")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String soccerFieldJson = objectMapper.writeValueAsString(soccerField);

        mockMvc.perform(post("/api/soccer-fields")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(soccerFieldJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("region").value("성남"))
                .andExpect(jsonPath("fieldName").value("모란 풋살장"));

        List<SoccerField> soccerFieldList = soccerFieldRepository.findAll();
        assertThat(soccerFieldList).hasSize(1);
        assertThat(soccerFieldList.get(0).getRegion()).isEqualTo("성남");
        assertThat(soccerFieldList.get(0).getFieldName()).isEqualTo("모란 풋살장");
    }

}
