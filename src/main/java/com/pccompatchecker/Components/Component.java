package com.pccompatchecker.Components;

import java.util.Optional;

public abstract class Component {
    private String name;
    private Optional<Double> price;
    private String brand;

    public Component(String name, Double price){
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

    private static final double USD_TO_PHP = 61.30;

    // USD to PHP
    @Override
    public String toString() {
        String priceLabel = price
                .map(p -> "₱" + String.format("%,.2f", p * USD_TO_PHP))
                .orElse("Price N/A");
        return name + " - " + priceLabel;
    }
}
