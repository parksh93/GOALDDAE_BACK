package com.goalddae.service;

import com.goalddae.entity.ArticleWorld;

import com.goalddae.repository.ArticleWorldJpaRepository;
import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

    private final ArticleWorldJpaRepository articleWorldJpaRepository;

    @Autowired
    public WebCrawlerServiceImpl(ArticleWorldJpaRepository articleWorldJpaRepository) {
        this.articleWorldJpaRepository = articleWorldJpaRepository;
    }

//    @PostConstruct  // 서버가 시작될 때 즉시 크롤링 시작
    @Scheduled(cron = "0 0 6,12,18,0 * * ?", zone = "Asia/Seoul")
    public void crawlArticles() {
        String URL = "https://sports.news.naver.com/wfootball/index.nhn";

        Document doc;

        try {
            doc = Jsoup.connect(URL)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .get();

            Elements newsElements = doc.select("ul.home_news_list > li > a");

            for (Element news : newsElements) {
                String title = news.attr("title");
                String url = news.absUrl("href");

                ArticleWorld articleWord = ArticleWorld.builder()
                        .title(title)
                        .url(url)
                        .build();
                articleWorldJpaRepository.save(articleWord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 유저에게 금일 기사만 보게끔
    public List<ArticleWorld> getTodayWorldArticles() {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        return articleWorldJpaRepository.findByCreatedAtAfter(startOfToday);
    }

    // 3일 이상된 데이터는 삭제 - 매일 자정에 실행됨
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    public void deleteOldArticles() {
        LocalDateTime tenDaysAgo = LocalDateTime.now().minusDays(3);
        articleWorldJpaRepository.deleteByCreatedAtBefore(tenDaysAgo);
    }
}