package com.gb.challenge.crawler;

public final class ManningCrawler implements Crawler {

    public ManningCrawler() {
    }

    @Override
    public String getIsbn(String site) {
        System.out.println(site);
        return "654654";
    }

}