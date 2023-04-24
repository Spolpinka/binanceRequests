package com.muh.binancerequests.service;

import com.muh.binancerequests.repositories.CostsDao;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RequestService {
    private static final String BASE_URL = "https://api.binance.com";
    private final CostsDao costsDao;
    OkHttpClient client = new OkHttpClient();

    public RequestService(CostsDao costsDao) {
        this.costsDao = costsDao;
    }

    public String getRubUsdt(String symbol) throws IOException {

        String endpoint = "/api/v3/ticker/24hr";
        String url = BASE_URL + endpoint + "?symbol=" + symbol;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();
        int firstPosition = result.indexOf("symbol");
        result = result.substring(firstPosition + "symbol".length());
//здесь надо вывести сумму и сохранить ее в базу

        return result;
    }

    public String getAvrCourse(String symbol) throws IOException {
        String endpoint = "/api/v3/avgPrice";
        String url = BASE_URL + endpoint + "?symbol=" + symbol;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();

        //просто сохранить как объект в базу
        String start = "price\":\"";
        Double price = Double.parseDouble(result.substring(result.indexOf(start) + start.length(), result.indexOf("\"}")));
        System.out.println(price);
        costsDao.add(price, symbol);

        return result;
    }

    //делаем длящийся запрос
    public void getTimeLapsRequests(String symbol) throws IOException, InterruptedException {
        String result = "";
        while (true) {
            result = getAvrCourse(symbol);
            System.out.println(result);
            Thread.sleep(1800000);
        }
    }
}
