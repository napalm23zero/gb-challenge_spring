package com.gb.challenge.crawler;

import java.net.MalformedURLException;

public interface Crawler {

    String getIsbn(String site) throws MalformedURLException;

}