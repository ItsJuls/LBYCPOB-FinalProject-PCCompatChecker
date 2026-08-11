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
}