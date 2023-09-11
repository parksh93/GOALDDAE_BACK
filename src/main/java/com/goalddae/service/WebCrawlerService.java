package com.goalddae.service;

import com.goalddae.entity.ArticleWorld;

import java.util.List;

public interface WebCrawlerService {
    void deleteOldArticles();
    List<ArticleWorld> getLatest25WorldArticles();
}