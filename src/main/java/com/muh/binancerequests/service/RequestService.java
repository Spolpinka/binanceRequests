package com.muh.binancerequests.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RequestService {
    private static final String BASE_URL = "https://api.binance.com";

    public String getRubUsdt() throws IOException {
        OkHttpClient client = new OkHttpClient();

        String symbol = "RUBUSDT";
        String endpoint = "/api/v3/ticker/24hr";
        String url = BASE_URL + endpoint + "?symbol=" + symbol;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }
}
