package com.pccompatchecker.Components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;

public class RAM extends Component {

    private int[] speed = new int[0];
    private int[] modules = new int[0];
    private final int casLatency;
    private final int firstWordLatency;
    private final Double pricePerGb;
    private final String color;
    private final boolean rgb;

    @JsonCreator
    public RAM(
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price,
            @JsonProperty("cas_latency") int casLatency,
            @JsonProperty("first_word_latency") int firstWordLatency,
            @JsonProperty("price_per_gb") Double pricePerGb,
            @JsonProperty("color") String color
    ) {
        super(name, price);
        this.casLatency = casLatency;
        this.firstWordLatency = firstWordLatency;
        this.pricePerGb = pricePerGb;
        this.color = color;
        this.rgb = name != null && name.toUpperCase().contains("RGB");
    }

    @JsonSetter("speed")
    public void setSpeed(JsonNode node) {
        this.speed = parseIntArray(node);
    }

    @JsonSetter("modules")
    public void setModules(JsonNode node) {
        this.modules = parseIntArray(node);
    }

    private static int[] parseIntArray(JsonNode node) {
        if (node == null || node.isNull()) return new int[0];
        if (node.isArray()) {
            int[] result = new int[node.size()];
            for (int i = 0; i < node.size(); i++) result[i] = node.get(i).asInt();
            return result;
        }
        return new int[]{ node.asInt() }; // scalar fallback
    }

    public int getGeneration() { return speed.length > 0 ? speed[0] : 0; }
    public int getSpeedMts() { return speed.length > 1 ? speed[1] : (speed.length > 0 ? speed[0] : 0); }

    public int getTotalCapacity() {
        if (modules.length >= 2) return modules[0] * modules[1];
        if (modules.length == 1) return modules[0];
        return 0;
    }

    public int[] getSpeed() { return speed; }
    public int[] getModules() { return modules; }
    public int getCasLatency() { return casLatency; }
    public int getFirstWordLatency() { return firstWordLatency; }
    public Double getPricePerGb() { return pricePerGb; }
    public String getColor() { return color; }
    public boolean isRgb() { return rgb; }

    @Override
    public String getCategory() { return "RAM"; }

    @Override
    public String getSpecSummary() {
        return getTotalCapacity() + "GB DDR" + getGeneration() + "-" + getSpeedMts();
    }
}