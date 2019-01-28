package com.gb.challenge.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static final String HREF = "href=['\"]([^'\"]+?)['\"]";
    public static final String ISBN = "978[0-9]{10,13}";
    public static final String SITE = "^.+?[^\\/:](?=[?\\/]|$)";

    public static Matcher createPatternMatcher(String regex, String data) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(data);
    }
}