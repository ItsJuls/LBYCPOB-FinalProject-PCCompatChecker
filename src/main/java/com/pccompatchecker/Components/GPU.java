package com.pccompatchecker.Components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GPU extends Component {

    private final String chipset;
    private final int memory; // VRAM in GB
    private final Double coreClock;
    private final Double boostClock;
    private final Double length;
    private final String color;

    @JsonCreator
    public GPU(
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price,
            @JsonProperty("chipset") String chipset,
            @JsonProperty("memory") int memory,
            @JsonProperty("core_clock") Double coreClock,
            @JsonProperty("boost_clock") Double boostClock,
            @JsonProperty("length") Double length,
            @JsonProperty("color") String color
    ) {
        super(name, price);
        this.chipset = chipset;
        this.memory = memory;
        this.coreClock = coreClock;
        this.boostClock = boostClock;
        this.length = length;
        this.color = color;
    }

    public String getChipset() { return chipset; }
    public int getMemory() { return memory; }
    public Double getCoreClock() { return coreClock; }
    public Double getBoostClock() { return boostClock; }
    public Double getLength() { return length; }
    public String getColor() { return color; }

    @Override
    public String getCategory() {
        return "GPU";
    }

    @Override
    public String getSpecSummary() {
        return chipset + ", " + memory + "GB VRAM";
    }

    @Override
    public String toString() {
        String base = super.toString();
        int dashIndex = base.indexOf(" - ");
        String namePart = dashIndex >= 0 ? base.substring(0, dashIndex) : base;
        String pricePart = dashIndex >= 0 ? base.substring(dashIndex) : "";
        String label = (chipset != null && !chipset.isBlank())
                ? namePart + " " + chipset
                : namePart;
        return label + pricePart;
    }
}