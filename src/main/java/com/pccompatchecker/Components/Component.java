package com.pccompatchecker.Components;

import java.util.Optional;

public abstract class Component {
    private String name;
    private Optional<Double> price;
    private String brand;

    public Component(String name, Double price){
        this.name = name;
        this.price = Optional.ofNullable(price);
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

    @Override
    public String toString() {
        return name + " - $" + price;
    }
}
