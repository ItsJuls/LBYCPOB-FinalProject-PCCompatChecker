package com.pccompatchecker.Components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Motherboard extends Component {
    private final String socket;
    private final String formFactor;
    private final Integer maxMemory; // nullable-safe wrapper
    private final Integer memorySlots;
    private final String color;

    @JsonCreator
    public Motherboard(
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price,
            @JsonProperty("socket") String socket,
            @JsonProperty("form_factor") String formFactor,
            @JsonProperty("max_memory") Integer maxMemory,
            @JsonProperty("memory_slots") Integer memorySlots,
            @JsonProperty("color") String color
    ) {
        super(name, price);
        this.socket = socket;
        this.formFactor = formFactor;
        this.maxMemory = maxMemory;
        this.memorySlots = memorySlots;
        this.color = color;
    }

    public String getSocket() { return socket; }
    public String getFormFactor() { return formFactor; }
    public Integer getMaxMemory() { return maxMemory; }
    public Integer getMemorySlots() { return memorySlots; }
    public String getColor() { return color; }

    @Override
    public String getCategory() {
        return "Motherboard";
    }

    @Override
    public String getSpecSummary() {
        return socket + ", " + formFactor + ", up to " + maxMemory + "GB RAM";
    }
}
