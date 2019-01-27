package com.gb.challenge.crawler;

public class RayWenderlichCrawler implements Crawler {

    @Override
    public String getIsbn(String site) {
        System.out.println(site);
        return "654654";
    }

}