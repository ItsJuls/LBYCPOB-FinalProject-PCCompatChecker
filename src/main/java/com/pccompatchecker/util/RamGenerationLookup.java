package com.pccompatchecker.util;

public class RamGenerationLookup {

    public static String getSupportedGeneration(String socket, String motherboardName) {
        if (socket == null) return "Unknown";

        switch (socket) {
            case "AM5":
            case "LGA1851":
                return "DDR5";
            case "AM4":
            case "LGA1200":
            case "LGA1151":
            case "LGA1150":
            case "LGA1155":
            case "LGA1156":
            case "LGA775":
            case "AM3+":
            case "AM3":
            case "AM3/AM2+/AM2":
            case "FM2+":
            case "FM2":
            case "FM1":
                return "DDR4"; // note: sockets like LGA1155/1150 etc. predate DDR4 in reality (DDR3-era) —
            case "LGA1700":
                // Dual-generation socket — infer from name since jsonl has no explicit field
                if (motherboardName != null && motherboardName.toUpperCase().contains("DDR4")) {
                    return "DDR4";
                }
                return "DDR5 (inferred — verify manually)";
            default:
                return "Unknown";
        }
    }
}