package com.pccompatchecker.util;

import java.util.HashMap;
import java.util.Map;

public class SocketLookup {

    private static final Map<String, String> SOCKET_MAP = new HashMap<>();

    static {
        // AMD
        SOCKET_MAP.put("K10", "AM3/AM2+/AM2");
        SOCKET_MAP.put("Piledriver", "AM3+");
        SOCKET_MAP.put("Bulldozer", "AM3+");
        SOCKET_MAP.put("Steamroller", "FM2+");
        SOCKET_MAP.put("Excavator", "FM2+"); // note: some Excavator chips (Bristol Ridge) used AM4 instead
        SOCKET_MAP.put("Lynx", "FM1");
        SOCKET_MAP.put("Jaguar", "Unknown"); // embedded/console APU, no desktop socket
        SOCKET_MAP.put("Puma+", "Unknown");  // embedded/mobile APU, no desktop socket
        SOCKET_MAP.put("Zen", "AM4");
        SOCKET_MAP.put("Zen+", "AM4");
        SOCKET_MAP.put("Zen 2", "AM4");
        SOCKET_MAP.put("Zen 3", "AM4");
        SOCKET_MAP.put("Zen 4", "AM5");
        SOCKET_MAP.put("Zen 5", "AM5");

        // Intel
        SOCKET_MAP.put("Core", "LGA775");           // Core 2 era desktop
        SOCKET_MAP.put("Wolfdale", "LGA775");
        SOCKET_MAP.put("Yorkfield", "LGA775");
        SOCKET_MAP.put("Nehalem", "LGA1366");        // note: Lynnfield variant used LGA1156 instead
        SOCKET_MAP.put("Westmere", "LGA1156");       // note: Gulftown variant used LGA1366 instead
        SOCKET_MAP.put("Sandy Bridge", "LGA1155");   // note: -E variant used LGA2011
        SOCKET_MAP.put("Ivy Bridge", "LGA1155");     // note: -E variant used LGA2011
        SOCKET_MAP.put("Haswell", "LGA1150");
        SOCKET_MAP.put("Haswell Refresh", "LGA1150");
        SOCKET_MAP.put("Broadwell", "LGA1150");      // note: -E variant used LGA2011-3
        SOCKET_MAP.put("Skylake", "LGA1151-v1");
        SOCKET_MAP.put("Kaby Lake", "LGA1151-v1");
        SOCKET_MAP.put("Coffee Lake", "LGA1151-v2");
        SOCKET_MAP.put("Coffee Lake Refresh", "LGA1151-v2");
        SOCKET_MAP.put("Cascade Lake", "LGA2066");
        SOCKET_MAP.put("Comet Lake", "LGA1200");
        SOCKET_MAP.put("Rocket Lake", "LGA1200");
        SOCKET_MAP.put("Alder Lake", "LGA1700");
        SOCKET_MAP.put("Raptor Lake", "LGA1700");
        SOCKET_MAP.put("Raptor Lake Refresh", "LGA1700");
        SOCKET_MAP.put("Arrow Lake", "LGA1851");
    }

    public static String getSocketFor(String microarchitecture) {
        return SOCKET_MAP.getOrDefault(microarchitecture, "Unknown");
    }
}