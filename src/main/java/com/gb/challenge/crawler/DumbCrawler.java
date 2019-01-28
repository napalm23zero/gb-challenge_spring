package com.gb.challenge.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;

import com.gb.challenge.utils.RegexUtils;

public class DumbCrawler implements Crawler {

    @Override
    public String getIsbn(String site) {

        try {
            URL url = new URL(site);
            BufferedReader bookBuffer = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            Boolean isbnFound = false;
            while ((inputLine = bookBuffer.readLine()) != null) {
                if (inputLine.toLowerCase().contains("isbn"))
                    isbnFound = true;
                if (isbnFound) {
                    Matcher matcher = RegexUtils.createPatternMatcher(RegexUtils.ISBN, inputLine);
                    if (matcher.find()) {
                        return matcher.group(0).replace("-", "");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unavailable";

    }

}