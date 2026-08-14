package com.pccompatchecker.controller.filter;

import com.pccompatchecker.Components.*;

import java.util.List;

/**
 * Builds the checkbox filter groups shown for each part category's filter
 * popup. Kept separate from MainController so the controller doesn't balloon
 * with filter-definition code.
 */
public class ComponentFilters {

    // ---- CPU -----------------------------------------------------------

    public static List<FilterGroup<CPU>> cpu() {
        return List.of(
                new FilterGroup<>("Brand", List.of(
                        new FilterOption<>("AMD", c -> "AMD".equalsIgnoreCase(c.getBrand())),
                        new FilterOption<>("Intel", c -> "Intel".equalsIgnoreCase(c.getBrand()))
                )),
                new FilterGroup<>("Socket", List.of(
                        new FilterOption<>("AM5", c -> socketBucket(c.getSocket()).equals("AM5")),
                        new FilterOption<>("AM4", c -> socketBucket(c.getSocket()).equals("AM4")),
                        new FilterOption<>("LGA1851", c -> socketBucket(c.getSocket()).equals("LGA1851")),
                        new FilterOption<>("LGA1700", c -> socketBucket(c.getSocket()).equals("LGA1700")),
                        new FilterOption<>("LGA1200", c -> socketBucket(c.getSocket()).equals("LGA1200")),
                        new FilterOption<>("LGA1151", c -> socketBucket(c.getSocket()).equals("LGA1151")),
                        new FilterOption<>("Other / Legacy", c -> socketBucket(c.getSocket()).equals("Other"))
                ))
        );
    }

    // ---- Motherboard -----------------------------------------------------

    public static List<FilterGroup<Motherboard>> motherboard() {
        return List.of(
                new FilterGroup<>("Form Factor", List.of(
                        new FilterOption<>("ATX", m -> "ATX".equalsIgnoreCase(m.getFormFactor())),
                        new FilterOption<>("Micro ATX", m -> "Micro ATX".equalsIgnoreCase(m.getFormFactor())),
                        new FilterOption<>("Mini ITX", m -> "Mini ITX".equalsIgnoreCase(m.getFormFactor())),
                        new FilterOption<>("EATX", m -> "EATX".equalsIgnoreCase(m.getFormFactor())),
                        new FilterOption<>("Other", m -> !List.of("ATX", "Micro ATX", "Mini ITX", "EATX")
                                .contains(m.getFormFactor() == null ? "" : m.getFormFactor()))
                )),
                new FilterGroup<>("Socket", List.of(
                        new FilterOption<>("AM5", m -> socketBucket(m.getSocket()).equals("AM5")),
                        new FilterOption<>("AM4", m -> socketBucket(m.getSocket()).equals("AM4")),
                        new FilterOption<>("LGA1851", m -> socketBucket(m.getSocket()).equals("LGA1851")),
                        new FilterOption<>("LGA1700", m -> socketBucket(m.getSocket()).equals("LGA1700")),
                        new FilterOption<>("LGA1200", m -> socketBucket(m.getSocket()).equals("LGA1200")),
                        new FilterOption<>("LGA1151", m -> socketBucket(m.getSocket()).equals("LGA1151")),
                        new FilterOption<>("Other / Legacy", m -> socketBucket(m.getSocket()).equals("Other"))
                ))
        );
    }

    private static String socketBucket(String rawSocket) {
        if (rawSocket == null) return "Other";
        for (String known : List.of("AM5", "AM4", "LGA1851", "LGA1700", "LGA1200", "LGA1151")) {
            if (rawSocket.contains(known)) return known;
        }
        return "Other";
    }

    // ---- RAM ---------------------------------------------------------

    public static List<FilterGroup<RAM>> ram() {
        return List.of(
                new FilterGroup<>("Generation", List.of(
                        new FilterOption<>("DDR5", r -> r.getGeneration() == 5),
                        new FilterOption<>("DDR4", r -> r.getGeneration() == 4),
                        new FilterOption<>("DDR3", r -> r.getGeneration() == 3),
                        new FilterOption<>("Other", r -> r.getGeneration() != 3 && r.getGeneration() != 4 && r.getGeneration() != 5)
                )),
                new FilterGroup<>("Total Capacity", List.of(
                        new FilterOption<>("8GB", r -> r.getTotalCapacity() == 8),
                        new FilterOption<>("16GB", r -> r.getTotalCapacity() == 16),
                        new FilterOption<>("32GB", r -> r.getTotalCapacity() == 32),
                        new FilterOption<>("64GB", r -> r.getTotalCapacity() == 64),
                        new FilterOption<>("128GB+", r -> r.getTotalCapacity() >= 128)
                ))
        );
    }

    // ---- GPU -----------------------------------------------------------

    public static List<FilterGroup<GPU>> gpu() {
        return List.of(
                new FilterGroup<>("Brand", List.of(
                        new FilterOption<>("Nvidia", g -> gpuBrand(g.getChipset()).equals("Nvidia")),
                        new FilterOption<>("AMD", g -> gpuBrand(g.getChipset()).equals("AMD")),
                        new FilterOption<>("Intel", g -> gpuBrand(g.getChipset()).equals("Intel"))
                )),
                new FilterGroup<>("VRAM", List.of(
                        new FilterOption<>("4GB or less", g -> g.getMemory() <= 4),
                        new FilterOption<>("6-8GB", g -> g.getMemory() >= 6 && g.getMemory() <= 8),
                        new FilterOption<>("10-12GB", g -> g.getMemory() >= 10 && g.getMemory() <= 12),
                        new FilterOption<>("16GB", g -> g.getMemory() == 16),
                        new FilterOption<>("20GB+", g -> g.getMemory() >= 20)
                ))
        );
    }

    private static String gpuBrand(String chipset) {
        if (chipset == null) return "Other";
        String c = chipset.toLowerCase();
        if (c.contains("radeon") || c.contains("firepro")) return "AMD";
        if (c.contains("arc")) return "Intel";
        return "Nvidia"; // GeForce, RTX, Quadro, Titan, NVS, etc.
    }

    // ---- Storage -----------------------------------------------------

    public static List<FilterGroup<Storage>> storage() {
        return List.of(
                new FilterGroup<>("Drive Type", List.of(
                        new FilterOption<>("SSD", Storage::isSsd),
                        new FilterOption<>("HDD", s -> !s.isSsd())
                )),
                new FilterGroup<>("Capacity", List.of(
                        new FilterOption<>("Under 500GB", s -> s.getCapacity() < 500),
                        new FilterOption<>("500GB - 1TB", s -> s.getCapacity() >= 500 && s.getCapacity() <= 1000),
                        new FilterOption<>("1TB - 2TB", s -> s.getCapacity() > 1000 && s.getCapacity() <= 2000),
                        new FilterOption<>("2TB - 4TB", s -> s.getCapacity() > 2000 && s.getCapacity() <= 4000),
                        new FilterOption<>("4TB+", s -> s.getCapacity() > 4000)
                ))
        );
    }

    // ---- CPU Cooler ----------------------------------------------------

    public static List<FilterGroup<CPUCooler>> cpuCooler() {
        return List.of(
                new FilterGroup<>("Type", List.of(
                        new FilterOption<>("Air Cooler", c -> !c.isAio()),
                        new FilterOption<>("AIO Liquid Cooler", CPUCooler::isAio)
                ))
        );
    }

    // ---- PSU -----------------------------------------------------------

    public static List<FilterGroup<PSU>> psu() {
        return List.of(
                new FilterGroup<>("Wattage", List.of(
                        new FilterOption<>("Under 550W", p -> p.getWattage() < 550),
                        new FilterOption<>("550-650W", p -> p.getWattage() >= 550 && p.getWattage() <= 650),
                        new FilterOption<>("650-850W", p -> p.getWattage() > 650 && p.getWattage() <= 850),
                        new FilterOption<>("850-1000W", p -> p.getWattage() > 850 && p.getWattage() <= 1000),
                        new FilterOption<>("1000W+", p -> p.getWattage() > 1000)
                )),
                new FilterGroup<>("Efficiency", List.of(
                        new FilterOption<>("Bronze", p -> "bronze".equalsIgnoreCase(p.getEfficiency())),
                        new FilterOption<>("Silver", p -> "silver".equalsIgnoreCase(p.getEfficiency())),
                        new FilterOption<>("Gold", p -> "gold".equalsIgnoreCase(p.getEfficiency())),
                        new FilterOption<>("Platinum", p -> "platinum".equalsIgnoreCase(p.getEfficiency())),
                        new FilterOption<>("Titanium", p -> "titanium".equalsIgnoreCase(p.getEfficiency())),
                        new FilterOption<>("Unrated", p -> p.getEfficiency() == null)
                )),
                new FilterGroup<>("SPL Tier", List.of(
                        new FilterOption<>("A (best)", p -> tierBucket(p.getTier()).equals("A")),
                        new FilterOption<>("B", p -> tierBucket(p.getTier()).equals("B")),
                        new FilterOption<>("C", p -> tierBucket(p.getTier()).equals("C")),
                        new FilterOption<>("D", p -> tierBucket(p.getTier()).equals("D")),
                        new FilterOption<>("E", p -> tierBucket(p.getTier()).equals("E")),
                        new FilterOption<>("F (worst)", p -> tierBucket(p.getTier()).equals("F")),
                        new FilterOption<>("Unrated", p -> tierBucket(p.getTier()).equals("Unrated"))
                ))
        );
    }

    /**
     * Collapses a raw SPL tier value like "A+", "B-*", "C*" down to its base
     * letter grade so the filter checklist shows 7 options instead of ~20.
     * The "*" suffix (limited confidence) and "+"/"-" modifiers are ignored
     * for filtering purposes.
     */
    private static String tierBucket(String rawTier) {
        if (rawTier == null || rawTier.isBlank() || rawTier.equalsIgnoreCase("Unrated")) {
            return "Unrated";
        }
        char letter = Character.toUpperCase(rawTier.charAt(0));
        if (letter >= 'A' && letter <= 'F') {
            return String.valueOf(letter);
        }
        return "Unrated";
    }

    // ---- Case ------------------------------------------------------------

    public static List<FilterGroup<Case>> pcCase() {
        return List.of(
                new FilterGroup<>("Form Factor", List.of(
                        new FilterOption<>("ATX", c -> caseFormFactor(c.getType()).equals("ATX")),
                        new FilterOption<>("MicroATX", c -> caseFormFactor(c.getType()).equals("MicroATX")),
                        new FilterOption<>("Mini ITX", c -> caseFormFactor(c.getType()).equals("Mini ITX")),
                        new FilterOption<>("Other", c -> caseFormFactor(c.getType()).equals("Other"))
                )),
                new FilterGroup<>("Style", List.of(
                        new FilterOption<>("Full Tower", c -> caseStyle(c.getType()).equals("Full Tower")),
                        new FilterOption<>("Mid Tower", c -> caseStyle(c.getType()).equals("Mid Tower")),
                        new FilterOption<>("Mini Tower", c -> caseStyle(c.getType()).equals("Mini Tower")),
                        new FilterOption<>("Desktop / Slim / HTPC", c -> caseStyle(c.getType()).equals("Desktop")),
                        new FilterOption<>("Test Bench", c -> caseStyle(c.getType()).equals("Test Bench"))
                ))
        );
    }

    private static String caseFormFactor(String rawType) {
        if (rawType == null) return "Other";
        String t = rawType.toLowerCase();
        if (t.contains("microatx")) return "MicroATX";
        if (t.contains("mini itx")) return "Mini ITX";
        if (t.contains("atx")) return "ATX";
        return "Other";
    }

    private static String caseStyle(String rawType) {
        if (rawType == null) return "Other";
        String t = rawType.toLowerCase();
        if (t.contains("full tower")) return "Full Tower";
        if (t.contains("mid tower")) return "Mid Tower";
        if (t.contains("mini tower")) return "Mini Tower";
        if (t.contains("test bench")) return "Test Bench";
        if (t.contains("desktop") || t.contains("slim") || t.contains("htpc")) return "Desktop";
        return "Other";
    }
}
