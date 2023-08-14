package com.goalddae.controller;

import com.goalddae.entity.SoccerField;
import com.goalddae.service.SoccerFieldService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

        // /search/soccerField 경로로 GET 요청을 수행하고, 지역과 축구장 이름을 파라미터로 전달
        mockMvc.perform(get("/search/soccerField")
                        .param("region", "성남")
                        .param("fieldName", "모란 풋살장"))
                .andExpect(status().isOk())
                .andExpect(result -> print());
    }
}