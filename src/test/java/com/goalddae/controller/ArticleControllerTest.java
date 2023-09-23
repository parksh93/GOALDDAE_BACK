package com.goalddae.controller;
import com.goalddae.service.WebCrawlerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebCrawlerService webCrawlerService;

    @Test
    @DisplayName("오늘의 최신 축구 기사 조회 테스트")
    public void findArticleWorld() throws Exception {
        // Given
        given(webCrawlerService.getLatest25WorldArticles()).willReturn(new ArrayList<>());

        // When, Then
        mockMvc.perform(get("/article/world")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}