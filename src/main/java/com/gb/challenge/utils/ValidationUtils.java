package com.gb.challenge.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ValidationUtils {

    public Boolean checkSite(URL url) {
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}