package com.pccompatchecker.Components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CPUCooler extends Component {

    private final int rpm;
    private final Double noiseLevel;
    private final String color;
    private final Integer size; // null = air cooler, otherwise AIO radiator size in mm

    @JsonCreator
    public CPUCooler(
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price,
            @JsonProperty("rpm") int rpm,
            @JsonProperty("noise_level") Double noiseLevel,
            @JsonProperty("color") String color,
            @JsonProperty("size") Integer size
    ) {
        super(name, price);
        this.rpm = rpm;
        this.noiseLevel = noiseLevel;
        this.color = color;
        this.size = size;
    }

    public int getRpm() { return rpm; }
    public Double getNoiseLevel() { return noiseLevel; }
    public String getColor() { return color; }
    public Integer getSize() { return size; }
    public boolean isAio() { return size != null; }

    @Override
    public String getCategory() {
        return "CPU Cooler";
    }

    @Override
    public String getSpecSummary() {
        return isAio() ? size + "mm AIO Liquid Cooler" : "Air Cooler";
    }
}