package com.goalddae.service;

import com.goalddae.entity.ArticleKorea;
import com.goalddae.entity.ArticleWorld;
import com.goalddae.repository.ArticleKoreaJpaRepository;
import com.goalddae.repository.ArticleWorldJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class WebCrawlerServiceTest {

    @Autowired
    WebCrawlerServiceImpl webCrawlerService;

    @Autowired
    ArticleWorldJpaRepository articleWorldJpaRepository;

    @Autowired
    ArticleKoreaJpaRepository articleKoreaJpaRepository;

    @Test
    @DisplayName("축구 기사 조회 서비스 테스트")
    public void findArticleTest() {
        // Given
        ArticleWorld articleWorld = ArticleWorld.builder()
                .title("해외 축구 기사")
                .url("http://test.com/world")
                .preview("미리보기")
                .build();
        articleWorldJpaRepository.save(articleWorld);

        ArticleKorea articleKorea = ArticleKorea.builder()
                .title("국내 축구 기사")
                .url("http://test.com/korea")
                .preview("미리보기")
                .build();
        articleKoreaJpaRepository.save(articleKorea);

        // When
        List<ArticleWorld> worldArticles = webCrawlerService.getWorldArticles();
        List<ArticleKorea> koreaArticles = webCrawlerService.getKoreaArticles();

        // Then
        assertFalse(worldArticles.isEmpty());
        assertFalse(koreaArticles.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("10일 지난 축구 기사 삭제 서비스 테스트")
    public void deleteOldArticleTest() {
        // Given: 25개의 해외 축구 기사와 국내 축구 기사를 생성하고 저장
        for (int i = 0; i < 25; i++) {
            LocalDateTime createdAt = LocalDateTime.now().minusSeconds(i);

            ArticleWorld articleWorld = ArticleWorld.builder()
                    .title("해외 축구 기사 " + i)
                    .url("http://test.com/world/" + i)
                    .createdAt(createdAt)
                    .build();
            articleWorldJpaRepository.save(articleWorld);

            ArticleKorea articleKorea = ArticleKorea.builder()
                    .title("국내 축구 기사 " + i)
                    .url("http://test.com/korea/" + i)
                    .createdAt(createdAt)
                    .build();
            articleKoreaJpaRepository.save(articleKorea);
        }

        // When: 최신 24개의 해외 축구 기사와 국내 축구 기사를 조회
        List<ArticleWorld> articleWorld = webCrawlerService.getWorldArticles();
        List<ArticleKorea> articleKorea = webCrawlerService.getKoreaArticles();

        // Then: 반환된 리스트의 크기는 각각 24개
        assertEquals(24, articleWorld.size());
        assertEquals(24, articleKorea.size());

        // Then: 리스트의 첫 번째 해외 축구 기사는 가장 마지막에 추가
        assertEquals("해외 축구 기사 24", articleWorld.get(0).getTitle());

        // Then: 리스트의 첫 번째 국내축 구기 사는 가장 마지막에 추가
        assertEquals("국내 축구 기사 24", articleKorea.get(0).getTitle());
    }
}