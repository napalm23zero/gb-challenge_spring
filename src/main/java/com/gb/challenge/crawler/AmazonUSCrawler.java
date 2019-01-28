package com.gb.challenge.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public final class AmazonUSCrawler implements Crawler {

    public AmazonUSCrawler() {
    }

    @Override
    public String getIsbn(String site) {
        try {
            Document document = Jsoup.connect(site).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31")
                    .timeout(2 * 1000).followRedirects(true).get();
            return document.body().select("#productDetailsTable > tbody > tr > td > div > ul > li:nth-child(5)").get(0)
                    .text().replace("ISBN-13: ", "").replace("\"", "").replace("-", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unavailable";
    }

}