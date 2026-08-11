package com.pccompatchecker.Components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Case extends Component {

    private final String type;              // e.g. "ATX Mid Tower"
    private final Integer bundledPsuWattage; // null if no PSU included
    private final String sidePanel;          // nullable
    private final Double externalVolume;     // nullable
    private final Integer internal35Bays;    // nullable
    private final String color;              // nullable

    @JsonCreator
    public Case(
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price,
            @JsonProperty("type") String type,
            @JsonProperty("psu") Integer bundledPsuWattage,
            @JsonProperty("side_panel") String sidePanel,
            @JsonProperty("external_volume") Double externalVolume,
            @JsonProperty("internal_35_bays") Integer internal35Bays,
            @JsonProperty("color") String color
    ) {
        super(name, price);
        this.type = type;
        this.bundledPsuWattage = bundledPsuWattage;
        this.sidePanel = sidePanel;
        this.externalVolume = externalVolume;
        this.internal35Bays = internal35Bays;
        this.color = color;
    }

    public String getType() { return type; }
    public Integer getBundledPsuWattage() { return bundledPsuWattage; }
    public boolean hasBundledPsu() { return bundledPsuWattage != null; }
    public String getSidePanel() { return sidePanel; }
    public Double getExternalVolume() { return externalVolume; }
    public Integer getInternal35Bays() { return internal35Bays; }
    public String getColor() { return color; }

    @Override
    public String getCategory() {
        return "Case";
    }

    @Override
    public String getSpecSummary() {
        return type + (hasBundledPsu() ? " (includes " + bundledPsuWattage + "W PSU)" : "");
    }
}