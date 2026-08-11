package com.pccompatchecker.Components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;

public class CPUCooler extends Component {

    private int rpmMin;
    private int rpmMax;
    private Double noiseLevelMin;
    private Double noiseLevelMax;
    private final String color;
    private final Integer size;

    @JsonCreator
    public CPUCooler(
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price,
            @JsonProperty("color") String color,
            @JsonProperty("size") Integer size
    ) {
        super(name, price);
        this.color = color;
        this.size = size;
    }

    @JsonSetter("rpm")
    public void setRpm(JsonNode node) {
        int[] r = parseIntRange(node);
        this.rpmMin = r[0];
        this.rpmMax = r[1];
    }

    @JsonSetter("noise_level")
    public void setNoiseLevel(JsonNode node) {
        if (node == null || node.isNull()) { noiseLevelMin = null; noiseLevelMax = null; return; }
        if (node.isArray()) {
            double min = Double.MAX_VALUE, max = -Double.MAX_VALUE;
            for (JsonNode n : node) { double v = n.asDouble(); min = Math.min(min, v); max = Math.max(max, v); }
            noiseLevelMin = min; noiseLevelMax = max;
        } else {
            double v = node.asDouble();
            noiseLevelMin = v; noiseLevelMax = v;
        }
    }

    private static int[] parseIntRange(JsonNode node) {
        if (node == null || node.isNull()) return new int[]{0, 0};
        if (node.isArray()) {
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for (JsonNode n : node) { int v = n.asInt(); min = Math.min(min, v); max = Math.max(max, v); }
            return new int[]{min, max};
        }
        int v = node.asInt();
        return new int[]{v, v};
    }

    public int getRpm() { return (rpmMin + rpmMax) / 2; } // avg, keeps old API working
    public int getRpmMin() { return rpmMin; }
    public int getRpmMax() { return rpmMax; }
    public Double getNoiseLevel() { return noiseLevelMin != null ? (noiseLevelMin + noiseLevelMax) / 2 : null; }
    public String getColor() { return color; }
    public Integer getSize() { return size; }
    public boolean isAio() { return size != null; }

    @Override
    public String getCategory() { return "CPU Cooler"; }

    @Override
    public String getSpecSummary() {
        return isAio() ? size + "mm AIO Liquid Cooler" : "Air Cooler";
    }
}