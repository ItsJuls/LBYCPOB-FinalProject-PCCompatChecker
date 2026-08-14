package com.pccompatchecker.Components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Storage extends Component {

    private final int capacity;
    private final String formFactor;
    private final String interfaceType;
    private final Integer cache;
    private final Double pricePerGb;
    private String driveType; // "SSD" or e.g. "HDD (7200 RPM)"

    @JsonCreator
    public Storage(
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price,
            @JsonProperty("capacity") int capacity,
            @JsonProperty("form_factor") String formFactor,
            @JsonProperty("interface") String interfaceType,
            @JsonProperty("cache") Integer cache,
            @JsonProperty("price_per_gb") Double pricePerGb
    ) {
        super(name, price);
        this.capacity = capacity;
        this.formFactor = formFactor;
        this.interfaceType = interfaceType;
        this.cache = cache;
        this.pricePerGb = pricePerGb;
    }

    @JsonSetter("type")
    public void setDriveType(Object raw) {
        if (raw == null) {
            this.driveType = "Unknown";
        } else if (raw instanceof String) {
            this.driveType = (String) raw; // "SSD"
        } else {
            this.driveType = "HDD (" + raw + " RPM)"; // numeric RPM values
        }
    }

    public int getCapacity() { return capacity; }
    public String getFormFactor() { return formFactor; }
    public String getInterfaceType() { return interfaceType; }
    public Integer getCache() { return cache; }
    public Double getPricePerGb() { return pricePerGb; }
    public String getDriveType() { return driveType; }
    public boolean isSsd() { return "SSD".equals(driveType); }

    @Override
    public String getCategory() {
        return "Storage";
    }

    @Override
    public String getSpecSummary() {
        return driveType + ", " + capacity + "GB, " + interfaceType;
    }

    private String formatCapacity() {
        if (capacity >= 1000) {
            double tb = capacity / 1000.0;
            // Show whole numbers as "4TB", fractional as "1.5TB"
            String tbStr = (tb == Math.floor(tb))
                    ? String.format("%.0f", tb)
                    : String.format("%.1f", tb);
            return tbStr + "TB";
        }
        return capacity + "GB";
    }

    @Override
    public String toString() {
        String base = super.toString(); // "<name> - <price/N/A>"
        int dashIndex = base.indexOf(" - ");
        String namePart = dashIndex >= 0 ? base.substring(0, dashIndex) : base;
        String pricePart = dashIndex >= 0 ? base.substring(dashIndex) : "";

        StringBuilder specs = new StringBuilder(formatCapacity());
        if (driveType != null && !driveType.isBlank()) {
            specs.append(" ").append(driveType);
        }

        return namePart + " " + specs + pricePart;
    }
}