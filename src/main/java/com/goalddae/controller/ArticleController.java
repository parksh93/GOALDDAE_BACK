package com.goalddae.controller;

import com.goalddae.entity.ArticleWorld;
import com.goalddae.service.WebCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final WebCrawlerService webCrawlerService;

    @Autowired
    public ArticleController(WebCrawlerService webCrawlerService) {
        this.webCrawlerService = webCrawlerService;
    }

    // 오늘의 해외 축구 뉴스 기사
    @GetMapping("/world")
    public List<ArticleWorld> todayArticleWorldFootball() {
        return webCrawlerService.getTodayWorldArticles();
    }
}
