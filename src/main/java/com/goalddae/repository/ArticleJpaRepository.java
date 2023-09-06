package com.goalddae.repository;

import com.goalddae.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleJpaRepository extends JpaRepository<Article, Long> {

}
