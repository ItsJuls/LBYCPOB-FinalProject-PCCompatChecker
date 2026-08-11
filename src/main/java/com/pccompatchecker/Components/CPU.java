package com.pccompatchecker.Components;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pccompatchecker.util.SocketLookup;

public class CPU extends Component {
    private final int coreCount;
    private final double coreClock;
    private final double boostClock;
    private final double tdp;
    private final String microarchitecture;
    private final String graphics; // nullable - null means no integrated graphics
    private final String socket; // derived, not in the raw jsonl

    @JsonCreator
    public CPU(
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price,
            @JsonProperty("core_count") int coreCount,
            @JsonProperty("core_clock") double coreClock,
            @JsonProperty("boost_clock") double boostClock,
            @JsonProperty("tdp") double tdp,
            @JsonProperty("microarchitecture") String microarchitecture,
            @JsonProperty("graphics") String graphics
    ) {
        super(name, price);
        this.coreCount = coreCount;
        this.coreClock = coreClock;
        this.boostClock = boostClock;
        this.tdp = tdp;
        this.microarchitecture = microarchitecture;
        this.graphics = graphics;
        this.socket = SocketLookup.getSocketFor(microarchitecture);
    }
    public int getCoreCount() { return coreCount; }
    public double getCoreClock() { return coreClock; }
    public double getBoostClock() { return boostClock; }
    public double getTdp() { return tdp; }
    public String getMicroarchitecture() { return microarchitecture; }
    public String getGraphics() { return graphics; }
    public String getSocket() { return socket; }
    public boolean hasIntegratedGraphics() { return graphics != null; }

    @Override
    public String getCategory() {
        return "CPU";
    }

    @Override
    public String getSpecSummary() {
        return coreCount + " cores, " + coreClock + "-" + boostClock + "GHz, "
                + socket + " (" + microarchitecture + ")";
    }
}