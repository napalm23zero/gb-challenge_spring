package com.gb.challenge.crawler;

public final class FundamentalKotlinCrawler implements Crawler {

    public FundamentalKotlinCrawler() {
    }

    @Override
    public String getIsbn(String site) {
        System.out.println(site);
        return "654654";
    }

}