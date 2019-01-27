package com.gb.challenge.crawler;

import org.jsoup.nodes.Document;

import java.io.IOException;

import org.jsoup.Jsoup;

public final class ManningCrawler implements Crawler {

    public ManningCrawler() {
    }

    @Override
    public String getIsbn(String site) {
        System.out.println("#######");
        try {
            Document document = Jsoup.connect(site).get();
            return document.body().select(
                    "#top > div.container.content-body > div > div > div > div.col-xs-12.product-header.visible-sm.visible-xs > div > div.product-authorship > div > ul > li:nth-child(2)")
                    .get(0).text().replace("ISBN ", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Unavailable";
    }

}