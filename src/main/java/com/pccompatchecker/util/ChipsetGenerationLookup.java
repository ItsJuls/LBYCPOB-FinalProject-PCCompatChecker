package com.pccompatchecker.util;

public class ChipsetGenerationLookup {

    private static final String[] LGA1151_V1_CHIPSETS = {"Z170", "H170", "B150", "H110", "Z270", "B250"};
    private static final String[] LGA1151_V2_CHIPSETS = {"Z370", "Z390", "B360", "B365", "H310", "H370", "Q370"};

    public static String resolveSocket(String rawSocket, String motherboardName) {
        if (!"LGA1151".equals(rawSocket) || motherboardName == null) {
            return rawSocket;
        }

        String upperName = motherboardName.toUpperCase();
        for (String chip : LGA1151_V1_CHIPSETS) {
            if (upperName.contains(chip)) return "LGA1151-v1";
        }
        for (String chip : LGA1151_V2_CHIPSETS) {
            if (upperName.contains(chip)) return "LGA1151-v2";
        }
        return "LGA1151-Unknown";
    }
}