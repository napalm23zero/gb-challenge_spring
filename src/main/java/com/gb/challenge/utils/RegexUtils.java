package com.gb.challenge.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    public static final String hrefRegex = "href=['\"]([^'\"]+?)['\"]";
    public static final String isbnRegex = "978[0-9]{10,13}";
    public static final String siteHomeRegex = "^.+?[^\\/:](?=[?\\/]|$)";

    public Matcher createPatternMatcher(String regex, String data) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(data);
    }
}