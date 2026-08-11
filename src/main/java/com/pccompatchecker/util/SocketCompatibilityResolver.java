package com.pccompatchecker.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SocketCompatibilityResolver {

    // Each entry: a CPU socket token -> the set of motherboard socket tokens that will accept it.
    // Backward compatibility only goes one direction (newer board accepts older CPU, not the reverse).
    private static final Map<String, Set<String>> ACCEPTED_BY = Map.ofEntries(
            Map.entry("AM2", Set.of("AM2", "AM2+", "AM3")),
            Map.entry("AM2+", Set.of("AM2+", "AM3")),
            Map.entry("AM3", Set.of("AM3", "AM3+")),
            Map.entry("AM3+", Set.of("AM3+")),
            Map.entry("FM1", Set.of("FM1")),
            Map.entry("FM2", Set.of("FM2", "FM2+")),
            Map.entry("FM2+", Set.of("FM2+"))
    );

    /**
     * Splits a possibly-compound socket string like "AM3/AM2+/AM2" into individual tokens.
     */
    private static Set<String> tokenize(String socket) {
        if (socket == null) return Set.of();
        return new HashSet<>(Arrays.asList(socket.split("/")));
    }

    /**
     * Returns true if a CPU with the given socket can physically/electrically go into
     * a motherboard listing the given (possibly compound) socket string.
     */
    public static boolean isCompatible(String cpuSocket, String moboSocket) {
        if (cpuSocket == null || moboSocket == null) return false;

        Set<String> cpuTokens = tokenize(cpuSocket);
        Set<String> moboTokens = tokenize(moboSocket);

        for (String cpuToken : cpuTokens) {
            Set<String> accepted = ACCEPTED_BY.getOrDefault(cpuToken, Set.of(cpuToken));
            for (String moboToken : moboTokens) {
                if (accepted.contains(moboToken)) {
                    return true;
                }
            }
        }
        return false;
    }
}