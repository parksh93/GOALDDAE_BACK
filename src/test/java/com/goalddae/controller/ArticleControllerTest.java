package com.goalddae.controller;
import com.goalddae.service.WebCrawlerServiceImpl;
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
    private WebCrawlerServiceImpl webCrawlerService;

    @Test
    @DisplayName("해외 축구 기사 조회 컨트롤러 테스트")
    public void findArticleWorld() throws Exception {
        // Given
        given(webCrawlerService.getWorldArticles()).willReturn(new ArrayList<>());

        // When, Then
        mockMvc.perform(get("/article/world")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("국내 축구 기사 조회 컨트롤러 테스트")
    public void findArticleKorea() throws Exception {
        // Given
        given(webCrawlerService.getKoreaArticles()).willReturn(new ArrayList<>());

        // When, Then
        mockMvc.perform(get("/article/korea")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
