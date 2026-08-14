package com.pccompatchecker.util;

import java.util.Map;

/**
 * Estimates a GPU's typical board power (TDP) in watts from its chipset name.
 *
 * The video-card dataset has no power field at all, so this mirrors the
 * SocketLookup approach used for CPUs: derive a value that isn't in the
 * raw data from something that is (here, the chipset string).
 *
 * Coverage focuses on chipsets people actually build with today (GTX 900
 * series onward, RX 400 series onward, Arc). Anything not in the table
 * falls back to a rough estimate based on VRAM size — good enough to keep
 * the compatibility check from silently skipping unusual/older cards.
 */
public class GpuPowerLookup {

    private static final Map<String, Integer> BOARD_POWER_WATTS = Map.ofEntries(
            // NVIDIA GeForce RTX 50 series
            Map.entry("GeForce RTX 5090", 575),
            Map.entry("GeForce RTX 5080", 360),
            Map.entry("GeForce RTX 5070 Ti", 300),
            Map.entry("GeForce RTX 5070", 250),
            Map.entry("GeForce RTX 5060 Ti", 180),
            Map.entry("GeForce RTX 5060", 145),
            Map.entry("GeForce RTX 5050", 130),

            // NVIDIA GeForce RTX 40 series
            Map.entry("GeForce RTX 4090", 450),
            Map.entry("GeForce RTX 4080 SUPER", 320),
            Map.entry("GeForce RTX 4080", 320),
            Map.entry("GeForce RTX 4070 Ti SUPER", 285),
            Map.entry("GeForce RTX 4070 Ti", 285),
            Map.entry("GeForce RTX 4070 SUPER", 220),
            Map.entry("GeForce RTX 4070", 200),
            Map.entry("GeForce RTX 4060 Ti", 160),
            Map.entry("GeForce RTX 4060", 115),

            // NVIDIA GeForce RTX 30 series
            Map.entry("GeForce RTX 3090 Ti", 450),
            Map.entry("GeForce RTX 3090", 350),
            Map.entry("GeForce RTX 3080 Ti", 350),
            Map.entry("GeForce RTX 3080 12GB LHR", 350),
            Map.entry("GeForce RTX 3080 10GB LHR", 320),
            Map.entry("GeForce RTX 3080 10GB", 320),
            Map.entry("GeForce RTX 3070 Ti", 290),
            Map.entry("GeForce RTX 3070 LHR", 220),
            Map.entry("GeForce RTX 3070", 220),
            Map.entry("GeForce RTX 3060 Ti LHR", 200),
            Map.entry("GeForce RTX 3060 Ti", 200),
            Map.entry("GeForce RTX 3060 12GB", 170),
            Map.entry("GeForce RTX 3060 8GB", 170),
            Map.entry("GeForce RTX 3050 8GB", 130),
            Map.entry("GeForce RTX 3050 6GB", 115),

            // NVIDIA GeForce RTX 20 series
            Map.entry("GeForce RTX 2080 Ti", 250),
            Map.entry("GeForce RTX 2080 SUPER", 250),
            Map.entry("GeForce RTX 2080", 215),
            Map.entry("GeForce RTX 2070 SUPER", 215),
            Map.entry("GeForce RTX 2070", 175),
            Map.entry("GeForce RTX 2060 SUPER", 175),
            Map.entry("GeForce RTX 2060 12GB", 184),
            Map.entry("GeForce RTX 2060", 160),

            // NVIDIA GeForce GTX 10 / 16 / 900 series
            Map.entry("GeForce GTX 1080 Ti", 250),
            Map.entry("GeForce GTX 1080", 180),
            Map.entry("GeForce GTX 1070 Ti", 180),
            Map.entry("GeForce GTX 1070", 150),
            Map.entry("GeForce GTX 1660 Ti", 120),
            Map.entry("GeForce GTX 1660 SUPER", 125),
            Map.entry("GeForce GTX 1660", 120),
            Map.entry("GeForce GTX 1650 SUPER", 100),
            Map.entry("GeForce GTX 1650 G6", 75),
            Map.entry("GeForce GTX 1650 G5", 75),
            Map.entry("GeForce GTX 1060 6GB", 120),
            Map.entry("GeForce GTX 1060 3GB", 120),
            Map.entry("GeForce GTX 1050 Ti", 75),
            Map.entry("GeForce GTX 1050", 75),
            Map.entry("GeForce GTX 1030", 30),
            Map.entry("GeForce GTX 1030 DDR4", 20),
            Map.entry("GeForce GTX 980 Ti", 250),
            Map.entry("GeForce GTX 980", 165),
            Map.entry("GeForce GTX 970", 145),
            Map.entry("GeForce GTX 960", 120),
            Map.entry("GeForce GTX 950", 90),

            // AMD Radeon RX 9000 / 7000 / 6000 series
            Map.entry("Radeon RX 9070 XT", 304),
            Map.entry("Radeon RX 9070", 220),
            Map.entry("Radeon RX 9060 XT", 182),
            Map.entry("Radeon RX 7900 XTX", 355),
            Map.entry("Radeon RX 7900 XT", 315),
            Map.entry("Radeon RX 7900 GRE", 260),
            Map.entry("Radeon RX 7800 XT", 263),
            Map.entry("Radeon RX 7700 XT", 245),
            Map.entry("Radeon RX 7600 XT", 190),
            Map.entry("Radeon RX 7600", 165),
            Map.entry("Radeon RX 6950 XT", 335),
            Map.entry("Radeon RX 6900 XT", 300),
            Map.entry("Radeon RX 6800 XT", 300),
            Map.entry("Radeon RX 6800", 250),
            Map.entry("Radeon RX 6750 XT", 250),
            Map.entry("Radeon RX 6700 XT", 230),
            Map.entry("Radeon RX 6700", 175),
            Map.entry("Radeon RX 6650 XT", 180),
            Map.entry("Radeon RX 6600 XT", 160),
            Map.entry("Radeon RX 6600", 132),
            Map.entry("Radeon RX 6500 XT", 107),
            Map.entry("Radeon RX 6400", 53),

            // AMD Radeon RX 500 / 5000 series
            Map.entry("Radeon RX 5700 XT", 225),
            Map.entry("Radeon RX 5700", 180),
            Map.entry("Radeon RX 5600 XT", 150),
            Map.entry("Radeon RX 5500 XT", 130),
            Map.entry("Radeon RX 590", 175),
            Map.entry("Radeon RX 580", 185),
            Map.entry("Radeon RX 570", 150),
            Map.entry("Radeon RX 560 - 1024", 80),
            Map.entry("Radeon RX 560 - 896", 80),
            Map.entry("Radeon RX 550 - 640", 50),
            Map.entry("Radeon RX 550 - 512", 50),
            Map.entry("Radeon RX 480", 150),
            Map.entry("Radeon RX 470", 120),
            Map.entry("Radeon RX 460", 75),

            // Intel Arc
            Map.entry("Arc B580", 190),
            Map.entry("Arc B570", 150),
            Map.entry("Arc A770", 225),
            Map.entry("Arc A750", 225),
            Map.entry("Arc A580", 175),
            Map.entry("Arc A380", 75),
            Map.entry("Arc A310", 35)
    );

    /**
     * Looks up a chipset's typical board power. Returns null if the chipset
     * isn't in the table so callers can fall back to a VRAM-based estimate
     * and flag the result as approximate.
     */
    public static Integer getExactWatts(String chipset) {
        if (chipset == null) return null;
        return BOARD_POWER_WATTS.get(chipset.trim());
    }

    /**
     * Rough fallback for chipsets not in the table (older/workstation cards),
     * based on VRAM size. Deliberately conservative — better to overestimate
     * required PSU wattage than to underestimate it.
     */
    public static int estimateWattsFromMemory(int memoryGb) {
        if (memoryGb <= 2) return 75;
        if (memoryGb <= 4) return 120;
        if (memoryGb <= 8) return 180;
        if (memoryGb <= 12) return 250;
        if (memoryGb <= 16) return 300;
        return 375;
    }
}
