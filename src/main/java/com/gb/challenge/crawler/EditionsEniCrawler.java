package com.gb.challenge.crawler;

import java.util.regex.Matcher;

import com.gb.challenge.utils.RegexUtils;

public final class EditionsEniCrawler implements Crawler {

    public EditionsEniCrawler() {
    }

    @Override
    public String getIsbn(String site) {
    
        RegexUtils regexUtils = new RegexUtils();
        Matcher matcher = regexUtils.createPatternMatcher(RegexUtils.isbnRegex, site);
        if (matcher.find())
            return matcher.group();
        return "Unavailable";

    }

}
