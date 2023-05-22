package com.muh.binancerequests.service;

import com.muh.binancerequests.repositories.CostsDao;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RequestService {
    private static final String BASE_URL = "https://api.binance.com";
    private final CostsDao costsDao;

    OkHttpClient client = new OkHttpClient();

    public RequestService(CostsDao costsDao, GraphCreator graphCreator) {
        this.costsDao = costsDao;
    }

    public String getRubUsdt(String symbol) throws IOException {

        String endpoint = "/api/v3/ticker/24hr";
        String url = BASE_URL + endpoint + "?symbol=" + symbol;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    public String getAvrCourse(String symbol) throws IOException {
        String endpoint = "/api/v3/avgPrice";
        String url = BASE_URL + endpoint + "?symbol=";
        if (symbol != null){
            url += symbol;
        }
        else {
            url += "USDTRUB";
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();

        //просто сохранить как объект в базу
        String start = "price\":\"";
        Double price = Double.parseDouble(result.substring(result.indexOf(start) + start.length(), result.indexOf("\"}")));
        costsDao.add(price, symbol);

        return result;
    }

    //делаем длящийся запрос
    public void getTimeLapsRequests(String symbol) throws IOException, InterruptedException {
        String result;
        while (true) {
            result = getAvrCourse(symbol);
            System.out.println(result);
            Thread.sleep(1_800_000);
        }
    }

    public String getRatio(String symbol1, String symbol2) throws IOException {
        String start = "price\":\"";
        String result1 = getAvrCourse(symbol1);
        System.out.println(result1);
        String result2 = getAvrCourse(symbol2);
        System.out.println(result2);
        Double price1 = Double.parseDouble(result1.substring(result1.indexOf(start) + start.length(), result1.indexOf("\"}")));
        System.out.println(price1);
        Double price2 = Double.parseDouble(result2.substring(result2.indexOf(start) + start.length(), result2.indexOf("\"}")));
        System.out.println(price2);
        return "" + price1 / price2;
    }

    public String exchangeInfo() throws IOException {
        String endpoint = "/api/v3/exchangeInfo";

        String url = BASE_URL + endpoint;

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }

    //получаем Мапу для графика
    public TreeMap<LocalDateTime, Double> getMapForChart() {
        return costsDao.getMapForChart();
    }

    public String getMonthCourses(int month){

        return costsDao.getMonthCourses(month);
    }

    public String getBestPrisesNow() {
        StringBuilder result = new StringBuilder();
        HttpClient httpClient = HttpClient.newHttpClient();
        String strRequestBuyRub = "https://p2p.binance.com/en/trade/TinkoffNew/USDT?fiat=RUB";

        String strRequestSellRub = "https://p2p.binance.com/en/trade/sell/USDT?fiat=RUB&payment=TinkoffNew";

        String strRequestBuyPhp = "https://p2p.binance.com/en/trade/all-payments/USDT?fiat=PHP";

        String strRequestSellPhp = "https://p2p.binance.com/en/trade/sell/USDT?fiat=PHP&payment=ALL";

        Map<String, String> requests = new TreeMap<>();

        String symbolRub = "₽";
        requests.put(strRequestBuyRub, symbolRub);
        requests.put(strRequestSellRub, symbolRub);
        String symbolPHP = "₱";
        requests.put(strRequestBuyPhp, symbolPHP);
        requests.put(strRequestSellPhp, symbolPHP);

        String responseBody = "";

        for (Map.Entry<String, String> entry : requests.entrySet()) {
            String symbol = entry.getValue();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(entry.getKey()))
                    .build();
            String symbolSQL = "";
            if (entry.getKey().equals(strRequestBuyRub)){
                symbolSQL = "USDTRUBBUY";
            }
            else if (entry.getKey().equals(strRequestSellRub)){
                symbolSQL = "USDTRUBSELL";
            } else if (entry.getKey().equals(strRequestBuyPhp)){
                symbolSQL = "USDTPHPBUY";
            } else if (entry.getKey().equals(strRequestSellPhp)){
                symbolSQL = "USDTPHPSELL";
            }
            try {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                responseBody = response.body();
                //System.out.println(responseBody);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            String startCourse = "<div class=\"css-1m1f8hn\">";
            String startVolume = "<div class=\"css-4cffwv\"><div data-bn-type=\"text\" class=\"css-vurnku\">" +
                    symbol + "</div>";
            String endVolume = "</div>";
//переменные для определения максимальной продажи и минимальной покупки в рамках одного символа
            Double minBuy = 1000000.0;
            Double maxSell = 0.0;
            //перебор всех символов и определение макимальной продажи и минимальной попупки
            //while (responseBody.contains(startCourse)) {
                String tempCourse = responseBody.substring(responseBody.indexOf(startCourse) +
                        startCourse.length(), responseBody.indexOf(startCourse) + startCourse.length() + 5);
                if (symbolSQL.contains("BUY")){
                    if (Double.parseDouble(tempCourse) < minBuy){
                        minBuy = Double.parseDouble(tempCourse);
                        //добавляем значение в базу
                        costsDao.add(minBuy, symbolSQL);
                        result.append(symbolSQL).append(" - ").append(minBuy).append("\n");
                    }
                } else {
                    if (Double.parseDouble(tempCourse) > maxSell){
                        maxSell = Double.parseDouble(tempCourse);
                        //добавляем значение в базу
                        costsDao.add(maxSell, symbolSQL);
                        result.append(symbolSQL).append(" - ").append(maxSell).append("\n");
                    }
                }
                responseBody = responseBody.substring(responseBody.indexOf(startVolume) + startVolume.length());
                String tempVolume1 = responseBody.substring(0, responseBody.indexOf(endVolume));
                System.out.println(tempVolume1);
                responseBody = responseBody.substring(responseBody.indexOf(startVolume) + startVolume.length());
                String tempVolume2 = responseBody.substring(0, responseBody.indexOf(endVolume));
                System.out.println(tempVolume2);

            //}
        }

        return result.toString();
    }

    public boolean transferToSql(){
        return costsDao.transferToSql();
    }
}
