package com.goalddae.service;

import com.goalddae.entity.ArticleWorld;
import com.goalddae.repository.ArticleWorldJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class WebCrawlerServiceTest {

    @Autowired
    WebCrawlerServiceImpl webCrawlerService;

    @Autowired
    ArticleWorldJpaRepository articleWorldJpaRepository;

    @Test
    @Transactional
    @DisplayName("축구 기사 조회 서비스 테스트")
    public void findArticleTest() {
        // Given
        ArticleWorld articleWorld = ArticleWorld.builder()
                .title("해외 축구 기사")
                .url("http://test.com/world")
                .build();
        articleWorldJpaRepository.save(articleWorld);

        // When
        List<ArticleWorld> worldArticles = webCrawlerService.getLatest25WorldArticles();

        // Then
        assertFalse(worldArticles.isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("해외 축구 기사 삭제 서비스 테스트")
    public void deleteArticleServiceTest() {
        // Given
        // 3일 전 기사 생성 및 저장
        ArticleWorld oldArticleWorld = ArticleWorld.builder()
                .id(1L)
                .title("3일 전 기사")
                .url("오래된 url")
                .createdAt(LocalDateTime.now().minusDays(3))
                .build();
        articleWorldJpaRepository.save(oldArticleWorld);

        // 현재 시간 기사 생성 및 저장
        ArticleWorld currentArticleWorld = ArticleWorld.builder()
                .title("최신 기사")
                .url("최신 url")
                .createdAt(LocalDateTime.now())
                .build();
        articleWorldJpaRepository.save(currentArticleWorld);

        // when
        webCrawlerService.deleteOldArticles();

        // then
        Optional<ArticleWorld> deletedArticle = articleWorldJpaRepository.findById(oldArticleWorld.getId());
        assertTrue(deletedArticle.isEmpty());

        assertFalse(articleWorldJpaRepository.findById(currentArticleWorld.getId()).isEmpty());
    }
}