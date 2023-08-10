package com.goalddae.controller;

import com.goalddae.entity.SoccerFiled;
import com.goalddae.repository.SoccerFiledRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(MainController.class)
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SoccerFiledRepository soccerFiledRepository;

    @Test
    public void testSearchSoccerFiled() throws Exception {
        // 가짜 데이터 생성
        List<SoccerFiled> fakeData = new ArrayList<>();
        SoccerFiled soccerFiled = new SoccerFiled("성남");
        fakeData.add(soccerFiled);

        // 가짜 데이터를 리턴하도록 설정
        when(soccerFiledRepository.findByRegion(anyString())).thenReturn(fakeData);

        // GET 요청을 보내고 결과를 검증
        mockMvc.perform(MockMvcRequestBuilders.get("/search/soccerFiled")
                        .param("keyword", "성남"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("soccerFiledList"))
                .andExpect(MockMvcResultMatchers.model().attribute("soccerFiledList", fakeData))
                .andExpect(MockMvcResultMatchers.view().name("search"));
    }
}
