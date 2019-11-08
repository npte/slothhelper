package ru.npte.sloth.slothhelper.httpclient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OkHttpClientWrapper implements HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpClientWrapper.class);

    private static final String TELEGRAM_SEND_MESSAGE_URL = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&parse_mode=HTML&text=%s";

    @Value( "${sloth.auc.url}" )
    private String aucUrl;

    public String get() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(aucUrl).get().build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            logger.error("Error while receiving auction list", e);
        }

        if (response == null || response.body() == null) {
            return null;
        }

        ResponseBody responseBody = response.body();

        if (responseBody == null) {
            return null;
        }

        String responseBodyString = null;

        try {
            responseBodyString = responseBody.string();
        } catch (IOException e) {
            logger.error("Error while receiving response body string", e);
        }

        return responseBodyString;
    }

    public void sendMessage(String botApiKey, String channelName, String message) {
        logger.info("sendin messaage {}", message);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(String.format(TELEGRAM_SEND_MESSAGE_URL, botApiKey, channelName, message))
                .get().build();
        try (Response resp = client.newCall(request).execute()) {
            logger.info("Gor response {}",  resp);
        } catch (IOException e) {
            logger.error("Error while sending message", e);
        }
    }
}
