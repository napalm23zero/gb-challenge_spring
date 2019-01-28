package com.gb.challenge.utils;

import java.util.Locale;

public class TextCleanupUtil {

    private TextCleanupUtil(){

    }

    public static String paragraph(String text){
        return text.replace("<p>", "").replace("</p>", "").trim().replaceAll("<[^>]*>", "");
    }

    public static String divLang(String text){
        return text.replace("<div class=\"book-lang\">", "").replace("</div>", "").trim()
        .toUpperCase(Locale.ENGLISH);
    }

    public static String h2SimpleTittle(String text){
        return text.replace("<h2>", "").replace("</h2>", "").trim();
    }

    public static String h2FirstTittle(String text){
        return text.replace("<h2 style=\"clear: left\">", "").replace("</h2>", "").trim();
    }
}