package com.gb.challenge.crawler;

public final class AmazonUSCrawler implements Crawler {

    public AmazonUSCrawler() {
    }

    @Override
    public String getIsbn(String site) {
        System.out.println(site);
        return "654654";
    }

}