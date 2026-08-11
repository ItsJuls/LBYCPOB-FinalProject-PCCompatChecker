package com.pccompatchecker.Components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class PSU extends Component {

    private final String type;          // "ATX", "SFX", etc.
    private final String efficiency;    // "gold", "bronze", etc. — nullable (410 nulls)
    private final int wattage;
    private String modular;             // normalized via setter below — raw is String OR Boolean
    private final String color;         // nullable
    private final String tier;          // SPL tier — "A", "B", "C", etc.
    private final int tierRank;
    private final boolean limitedConfidence;

    @JsonCreator
    public PSU(
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price,
            @JsonProperty("type") String type,
            @JsonProperty("efficiency") String efficiency,
            @JsonProperty("wattage") int wattage,
            @JsonProperty("color") String color,
            @JsonProperty("tier") String tier,
            @JsonProperty("tier_rank") int tierRank,
            @JsonProperty("limited_confidence") boolean limitedConfidence
    ) {
        super(name, price);
        this.type = type;
        this.efficiency = efficiency;
        this.wattage = wattage;
        this.color = color;
        this.tier = tier;
        this.tierRank = tierRank;
        this.limitedConfidence = limitedConfidence;
    }


    @JsonSetter("modular")
    public void setModular(Object raw) {
        if (raw == null) {
            this.modular = "Unknown";
        } else if (raw instanceof Boolean) {
            this.modular = ((Boolean) raw) ? "Yes" : "None";
        } else {
            this.modular = raw.toString(); // "Full", "Semi", "Full / Side"
        }
    }

    public String getType() { return type; }
    public String getEfficiency() { return efficiency; }
    public int getWattage() { return wattage; }
    public String getModular() { return modular; }
    public String getColor() { return color; }
    public String getTier() { return tier; }
    public int getTierRank() { return tierRank; }
    public boolean isLimitedConfidence() { return limitedConfidence; }

    @Override
    public String getCategory() {
        return "PSU";
    }

    @Override
    public String getSpecSummary() {
        return wattage + "W, " + efficiency + ", Tier " + tier + " (" + modular + " Modular)";
    }
}