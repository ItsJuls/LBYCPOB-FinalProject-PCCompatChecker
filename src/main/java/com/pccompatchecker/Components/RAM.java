package com.pccompatchecker.Components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RAM extends Component {

    private final int[] speed;
    private final int[] modules;
    private final int casLatency;
    private final int firstWordLatency;
    private final Double pricePerGb;
    private final String color;
    private final boolean rgb;

    @JsonCreator
    public RAM(
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price,
            @JsonProperty("speed") int[] speed,
            @JsonProperty("modules") int[] modules,
            @JsonProperty("cas_latency") int casLatency,
            @JsonProperty("first_word_latency") int firstWordLatency,
            @JsonProperty("price_per_gb") Double pricePerGb,
            @JsonProperty("color") String color
    ) {
        super(name, price);
        this.speed = speed;
        this.modules = modules;
        this.casLatency = casLatency;
        this.firstWordLatency = firstWordLatency;
        this.pricePerGb = pricePerGb;
        this.color = color;
        this.rgb = name != null && name.toUpperCase().contains("RGB");
    }

    public int getGeneration() {
        return speed[0];
    }

    public int getSpeedMts() {
        return speed[1];
    }

    public int getTotalCapacity() {
        return modules[0] * modules[1];
    }

    public int[] getSpeed() {
        return speed;
    }

    public int[] getModules() {
        return modules;
    }

    public int getCasLatency() {
        return casLatency;
    }

    public int getFirstWordLatency() {
        return firstWordLatency;
    }

    public Double getPricePerGb() {
        return pricePerGb;
    }

    public String getColor() {
        return color;
    }

    public boolean isRgb() {
        return rgb;
    }

    @Override
    public String getCategory() {
        return "RAM";
    }

    @Override
    public String getSpecSummary() {
        return getTotalCapacity() + "GB DDR" + getGeneration() + "-" + getSpeedMts();
    }
}