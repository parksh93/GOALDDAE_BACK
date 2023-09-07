package com.goalddae.service;

import com.goalddae.entity.ArticleKorea;
import com.goalddae.entity.ArticleWorld;

import java.util.List;

public interface WebCrawlerService {
    List<ArticleWorld> getWorldArticles();
    List<ArticleKorea> getKoreaArticles();
}
