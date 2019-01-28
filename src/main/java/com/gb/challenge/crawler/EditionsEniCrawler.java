package com.gb.challenge.crawler;

import java.util.regex.Matcher;

import com.gb.challenge.utils.RegexUtil;

public final class EditionsEniCrawler implements Crawler {

    public EditionsEniCrawler() {
    }

    @Override
    public String getIsbn(String site) {

        Matcher matcher = RegexUtil.createPatternMatcher(RegexUtil.ISBN, site);
        if (matcher.find())
            return matcher.group();
        return "Unavailable";

    }

}
