package com.pccompatchecker.Components;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Component {

    private String name;
    private Optional<Double> price;
    private String brand;

    // Fallback rate if the internet/API is unavailable
    private static final double FALLBACK_USD_TO_PHP = 61.30;

    // Cached exchange rate for the current app session
    private static volatile double usdToPhp = FALLBACK_USD_TO_PHP;

    private static final HttpClient HTTP_CLIENT =
            HttpClient.newHttpClient();

    public Component(String name, Double price) {
        this.name = name;
        this.price = Optional.ofNullable(price);
        this.brand = name != null ? name.split(" ")[0] : "Unknown";
    }

    public String getName() {
        return name;
    }

    public Optional<Double> getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public abstract String getCategory();

    public abstract String getSpecSummary();

    /**
     * Returns the currently cached USD to PHP exchange rate.
     */
    public static double getUsdToPhp() {
        return usdToPhp;
    }

    /**
     * Gets the current USD to PHP exchange rate from Frankfurter.
     * If the request fails, the hardcoded fallback rate is kept.
     *
     * This should be called once when the application starts.
     */
    public static void updateExchangeRate() {

        String url =
                "https://api.frankfurter.app/latest?base=USD&symbols=PHP";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    HTTP_CLIENT.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() == 200) {

                String json = response.body();

                // Extract the PHP value from:
                // {"amount":1.0,"base":"USD","date":"...","rates":{"PHP":61.46}}
                Pattern pattern =
                        Pattern.compile("\"PHP\"\\s*:\\s*([0-9.]+)");

                Matcher matcher = pattern.matcher(json);

                if (matcher.find()) {

                    double rate =
                            Double.parseDouble(matcher.group(1));

                    if (rate > 0) {
                        usdToPhp = rate;

                        System.out.println(
                                "Live USD → PHP rate: ₱"
                                        + String.format("%.4f", usdToPhp)
                        );

                        return;
                    }
                }
            }

            System.out.println(
                    "Could not get live exchange rate. "
                            + "Using fallback: ₱"
                            + FALLBACK_USD_TO_PHP
            );

        } catch (IOException | InterruptedException | RuntimeException e) {

            System.out.println(
                    "Forex API unavailable. "
                            + "Using fallback rate: ₱"
                            + FALLBACK_USD_TO_PHP
            );

            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Converts the component price from USD to PHP
     * using the cached session exchange rate.
     */
    public Optional<Double> getPricePhp() {
        return price.map(p -> p * usdToPhp);
    }

    @Override
    public String toString() {

        String priceLabel = getPricePhp()
                .map(p -> "₱" + String.format("%,.2f", p))
                .orElse("Price N/A");

        return name + " - " + priceLabel;
    }
}