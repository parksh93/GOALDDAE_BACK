package com.goalddae.repository.Article;

import com.goalddae.entity.ArticleWorld;
import com.goalddae.repository.ArticleWorldJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
public class ArticleJpaRepositoryTest {

    @Autowired
    private ArticleWorldJpaRepository articleWorldJpaRepository;

    @Test
    @Transactional
    @DisplayName("최신 뉴스 25개 조회")
    public void latestArticleTest() {
        // Given: 25개의 기사를 생성하고 저장합니다.
        for (int i = 1; i <= 25; i++) {
            LocalDateTime createdAt = LocalDateTime.now().minusSeconds(i);
            ArticleWorld article = ArticleWorld.builder()
                    .title("제목 " + i)
                    .url("http://example.com/article/" + i)
                    .createdAt(createdAt)
                    .build();

            articleWorldJpaRepository.save(article);
        }

        // When: 최신 25개의 기사를 조회
        List<ArticleWorld> latestArticles = articleWorldJpaRepository.findTop25ByOrderByCreatedAtDesc();

        // Then: 반환된 리스트의 크기는 25
        assertEquals(25, latestArticles.size());

        // Then: 리스트의 첫 번째 기사는 가장 마지막에 추가한 기사 ("제목 25")
        assertEquals("제목 25", latestArticles.get(0).getTitle());
    }

    @Test
    @Transactional
    @DisplayName("해외 축구 기사 삭제 레포지토리 테스트")
    public void deleteArticleTest() {
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

        // when : 10일 이전의 기사 삭제
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
        articleWorldJpaRepository.deleteByCreatedAtBefore(threeDaysAgo);

        // then
        // 11일 전 기사는 삭제되었으므로 oldArticleWorld 객체가 없는 것 확인
        Optional<ArticleWorld> deleteArticle = articleWorldJpaRepository.findById(oldArticleWorld.getId());
        assertTrue(deleteArticle.isEmpty());

        // 현재 시간에 생성된 currentArticleWorld 객체가 있는지 확인
        assertFalse(articleWorldJpaRepository.findById(currentArticleWorld.getId()).isEmpty());
    }
}