package com.gb.challenge.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PacktPubCrawler implements Crawler {

    @Override
    public String getIsbn(String site) {
        try {
            Document document = Jsoup.connect(site).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31")
                    .timeout(2 * 1000).followRedirects(true).get();
            return document.body().select(
                    "#main-book > div:nth-child(2) > div > div.book-info-details.onlyDesktop > div > div.book-info-isbn13 > span:nth-child(2)")
                    .get(0).text().replace("ISBN ", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unavailable";
    }
}