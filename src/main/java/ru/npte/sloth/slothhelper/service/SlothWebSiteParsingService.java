package ru.npte.sloth.slothhelper.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@EnableScheduling
public class SlothWebSiteParsingService {

    private static final Logger logger = LoggerFactory.getLogger(SlothWebSiteParsingService.class);

    @Value( "${sloth.auc.url}" )
    private String aucUrl;

    @Scheduled(fixedRate=10000)
    public void printMessage() {
        logger.info("This method executes by scheduler every 10 sec");
    }

    /*public List<String> getAucItemsNames() throws IOException {
        String URL = "http://www.slothmud.org/wp/live-info/live-auctions";

        return Jsoup.connect(URL).get()
            .getElementsByTag("tr").stream().skip(3).map(
                s -> {
                    Elements trs = s.getElementsByTag("td");
                    return new AucItem(trs).toString();
                }
            ).collect(Collectors.toList());
    }*/

    public List<String> getAucItemsNames() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(aucUrl).get().build();

        try {
            Response response = client.newCall(request).execute();

            ResponseBody responseBody = response.body();

            if (responseBody != null) {
                return Arrays.asList(responseBody.string().split("\\r?\\n"))
                        .stream()
                        .map();
            }

            String serverAnswer = .string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }


    private static class AucItem {
        private String num;
        private String name;
        private String current;
        private String buyout;
        private String ends;
        private String searchLink;

        AucItem(Elements elements) {
            this.num = elements.get(0).text();
            this.name = elements.get(1).text();
            this.current = elements.get(4).text();
            this.buyout = elements.get(5).text();
            this.ends = elements.get(6).text();
            this.searchLink = "http://slothmudeq.tk/?search=" + this.name.replaceAll("(^a )|(^A )|(^an )|(^An )|(^the )|(^The )", "").replace("'", "%27").replace(" ", "+");
        }

        public String toString() {
            return new StringBuilder().append(this.num).append(". ")
            .append("<a href=\"").append(this.searchLink).append("\">").append(this.name).append("</a>")
            .append("\r\nCurrent: ").append(this.current).append("\r\nBuyout: ").append(this.buyout).append("\r\nEnds: ").append(this.ends).toString();
        }
    }

}