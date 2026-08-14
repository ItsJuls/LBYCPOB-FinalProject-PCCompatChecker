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

    public static final double USD_TO_PHP = 61.30;

    /** Price converted to PHP, empty if the source data had no price for this part. */
    public Optional<Double> getPricePhp() {
        return price.map(p -> p * USD_TO_PHP);
    }

    // USD to PHP
    @Override
    public String toString() {
        String priceLabel = getPricePhp()
                .map(p -> "₱" + String.format("%,.2f", p))
                .orElse("Price N/A");
        return name + " - " + priceLabel;
    }
}
