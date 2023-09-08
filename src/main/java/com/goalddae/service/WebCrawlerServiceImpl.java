package com.goalddae.service;

import com.goalddae.entity.ArticleKorea;
import com.goalddae.entity.ArticleWorld;
import com.goalddae.repository.ArticleKoreaJpaRepository;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

    private final ArticleWorldJpaRepository articleWorldJpaRepository;
    private final ArticleKoreaJpaRepository articleKoreaJpaRepository;

    @Autowired
    public WebCrawlerServiceImpl(ArticleWorldJpaRepository articleWorldJpaRepository,
                                 ArticleKoreaJpaRepository articleKoreaJpaRepository) {
        this.articleWorldJpaRepository = articleWorldJpaRepository;
        this.articleKoreaJpaRepository = articleKoreaJpaRepository;
    }

    //    @PostConstruct  // 서버가 시작될 때 즉시 크롤링 시작
    @Scheduled(cron = "0 0 6,12,18,0 * * ?", zone = "Asia/Seoul")
    public void crawlArticles() {
        String[] URLs = {
                "https://sports.news.naver.com/wfootball/index.nhn",
                "https://sports.news.naver.com/kfootball/index.nhn"
        };

        for (String URL : URLs) {
            Document doc;

            try {
                doc = Jsoup.connect(URL)
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                        .get();

                Elements newsElements = doc.select("ul.home_news_list > li > a");

                for (Element news : newsElements) {
                    String title = news.attr("title");
                    String url = news.absUrl("href");
                    String preview = "미리보기";

                    if(URL.contains("/wfootball")) { // 세계 축구인 경우
                        ArticleWorld articleWord = ArticleWorld.builder()
                                .title(title)
                                .url(url)
                                .preview(preview)
                                .build();
                        articleWorldJpaRepository.save(articleWord);
                    } else if(URL.contains("/kfootball")) { // 국내 축구인 경우
                        ArticleKorea articleKorea = ArticleKorea.builder()
                                .title(title)
                                .url(url)
                                .preview(preview)
                                .build();
                        articleKoreaJpaRepository.save(articleKorea);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 조회 - 해외 축구 최신 기사 24개
    @Override
    public List<ArticleWorld> getWorldArticles() {
        return articleWorldJpaRepository.findTop25ByOrderByCreatedAtDesc();
    }

    // 조회 - 국내 축구 최신 기사 24개
    @Override
    public List<ArticleKorea> getKoreaArticles() {
        return articleKoreaJpaRepository.findTop25ByOrderByCreatedAtDesc();
    }

    // 10일 이상된 데이터는 삭제 - 매일 자정에 실행됨
    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul")
    public void deleteOldArticles() {
        LocalDateTime tenDaysAgo = LocalDateTime.now().minusDays(10);
        articleWorldJpaRepository.deleteByCreatedAtBefore(tenDaysAgo);
        articleKoreaJpaRepository.deleteByCreatedAtBefore(tenDaysAgo);
    }
}