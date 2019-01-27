package com.gb.challenge.crawler;

public final class AmazonDECrawler implements Crawler {

    public AmazonDECrawler() {
    }

    @Override
    public String getIsbn(String site) {
        System.out.println(site);
        return "654654";
    }

}