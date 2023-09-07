package com.goalddae.repository;

import com.goalddae.entity.ArticleWorld;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleWorldJpaRepository extends JpaRepository<ArticleWorld, Long> {
    // 일정 시간에 기사 데이터 삭제
    void deleteByCreatedAtBefore(LocalDateTime cutoff);
    // 유저에게 최신 뉴스 24개만 보이게
    List<ArticleWorld> findTop24ByOrderByCreatedAtDesc();
}
    