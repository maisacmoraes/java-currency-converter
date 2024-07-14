package main.java.com.maisa.currencyconverter.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CurrencyConverterService {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/41090ddd19c05b38336696d7/latest/USD";
    private Map<String, Double> conversionRates;

    public CurrencyConverterService() throws IOException, InterruptedException {
        fetchConversionRates();
    }

    private void fetchConversionRates() throws IOException, InterruptedException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            parseConversionRates(response.body());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while fetching exchange rates", e);
        }
    }

    private void parseConversionRates(String responseBody) {
        Gson gson = new Gson();
        Map<String, Object> responseMap = gson.fromJson(responseBody, new TypeToken<Map<String, Object>>(){}.getType());
        conversionRates = (Map<String, Double>) responseMap.get("conversion_rates");
    }

    public double convert(String fromCurrency, String toCurrency, double amount) {
        if (!conversionRates.containsKey(fromCurrency) || !conversionRates.containsKey(toCurrency)) {
            throw new IllegalArgumentException("Invalid currency code");
        }
        double fromRate = conversionRates.get(fromCurrency);
        double toRate = conversionRates.get(toCurrency);
        return amount * (toRate / fromRate);
    }
}
