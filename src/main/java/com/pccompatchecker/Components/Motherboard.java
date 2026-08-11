package com.pccompatchecker.Components;

public class Motherboard extends Component {
    private final String socket;
    private final String formFactor;
    private final Integer maxMemory; // nullable-safe wrapper
    private final Integer memorySlots;
    private final String color;

    public Motherboard(String name, Double price, String socket, String formFactor,
                       Integer maxMemory, Integer memorySlots, String color) {
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
