package com.goalddae.service;

import com.goalddae.entity.ArticleWorld;
import com.goalddae.repository.ArticleWorldJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class WebCrawlerServiceTest {

    @Autowired
    WebCrawlerServiceImpl webCrawlerService;

    @Autowired
    ArticleWorldJpaRepository articleWorldJpaRepository;

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

        // When
        List<ArticleWorld> worldArticles = webCrawlerService.getTodayWorldArticles();

        // Then
        assertFalse(worldArticles.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("10일 지난 축구 기사 삭제 서비스 테스트")
    public void deleteOldArticleTest() {
        // Given: 25개의 해외 축구 기사와 국내 축구 기사를 생성하고 저장
        for (int i = 0; i < 26; i++) {
            LocalDateTime createdAt = LocalDateTime.now().minusSeconds(i);

            ArticleWorld articleWorld = ArticleWorld.builder()
                    .title("해외 축구 기사 " + i)
                    .url("http://test.com/world/" + i)
                    .createdAt(createdAt)
                    .build();
            articleWorldJpaRepository.save(articleWorld);
        }

        // When: 최신 25개의 해외 축구 기사와 국내 축구 기사를 조회
        List<ArticleWorld> articleWorld = webCrawlerService.getTodayWorldArticles();

        // Then: 반환된 리스트의 크기는 각각 25개
        assertEquals(25, articleWorld.size());

        // Then: 리스트의 첫 번째 해외 축구 기사는 가장 마지막에 추가
        assertEquals("해외 축구 기사 24", articleWorld.get(0).getTitle());
    }
}