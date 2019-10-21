package ru.npte.sloth.slothhelper.httpclient;

public interface HttpClient {

    String get();

    void sendMessage(String botApiKey, String channelName, String message);
}
