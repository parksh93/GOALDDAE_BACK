package com.goalddae.service;

import com.goalddae.entity.Article;
import com.goalddae.repository.ArticleJpaRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

    private final ArticleJpaRepository articleJpaRepository;

    @Autowired
    public WebCrawlerServiceImpl(ArticleJpaRepository articleJpaRepository) {
        this.articleJpaRepository = articleJpaRepository;
    }

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
                String preview = "미리보기";

                Article article = Article.builder()
                        .title(title)
                        .url(url)
                        .preview(preview)
                        .build();

                articleJpaRepository.save(article);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
