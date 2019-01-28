package com.gb.challenge.crawler;

import java.io.IOException;
import java.util.regex.Matcher;

import com.gb.challenge.utils.RegexUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public final class ManningCrawler implements Crawler {

    public ManningCrawler() {
    }

    @Override
    public String getIsbn(String site) {
        try {
            Document document = Jsoup.connect(site).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31")
                    .timeout(2 * 1000).followRedirects(true).get();
            String html = document.body().select(
                    "#top > div.container.content-body > div > div > div > div.col-sm-9.col-xs-12 > div > div.col-md-12.product-header.hidden-sm.hidden-xs > div > div.product-authorship > div")
                    .get(0).text();
            Matcher matcher = RegexUtil.createPatternMatcher(RegexUtil.ISBN, html);
            if (matcher.find())
                return matcher.group();
            return "Unavailable";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unavailable";
    }

}