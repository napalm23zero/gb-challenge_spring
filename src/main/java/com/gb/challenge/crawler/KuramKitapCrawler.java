package com.gb.challenge.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class KuramKitapCrawler implements Crawler {

    @Override
    public String getIsbn(String site) {
        try {
            Document document = Jsoup.connect(site).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31")
                    .timeout(2 * 1000).followRedirects(true).get();
            return document.body().select(
                    "#tab_95_3 > li > div.box.box_prd.box_prd_3.box_prd_detail > div.box_content > div.table-block.view-table.prd_custom_fields > div:nth-child(1) > div:nth-child(3)")
                    .get(0).text().replace("- ", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unavailable";
    }

}
