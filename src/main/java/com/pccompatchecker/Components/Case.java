package com.pccompatchecker.Components;

public class Case extends Component {

    private final String type;              // e.g. "ATX Mid Tower"
    private final Integer bundledPsuWattage; // null if no PSU included
    private final String sidePanel;          // nullable
    private final Double externalVolume;     // nullable
    private final Integer internal35Bays;    // nullable
    private final String color;              // nullable

    public Case(String name, Double price, String type, Integer bundledPsuWattage,
                String sidePanel, Double externalVolume, Integer internal35Bays, String color) {
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