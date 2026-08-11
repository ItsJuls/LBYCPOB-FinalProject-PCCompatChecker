package com.pccompatchecker.Compatibility.rules;

import com.pccompatchecker.Compatibility.CompatibilityResult;
import com.pccompatchecker.Compatibility.CompatibilityRule;
import com.pccompatchecker.build.Build;
import com.pccompatchecker.Components.CPU;
import com.pccompatchecker.Components.Motherboard;
import com.pccompatchecker.util.ChipsetGenerationLookup;
import com.pccompatchecker.util.SocketCompatibilityResolver;

import java.util.Optional;

public class SocketCompatibilityRule implements CompatibilityRule {

    @Override
    public CompatibilityResult check(Build build) {
        Optional<CPU> cpu = build.getCpu();
        Optional<Motherboard> motherboard = build.getMotherboard();

        if (cpu.isEmpty() || motherboard.isEmpty()) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.NOT_APPLICABLE,
                    "CPU or motherboard not selected yet",
                    "Socket Compatibility"
            );
        }

        String cpuSocket = cpu.get().getSocket();

        if (cpuSocket == null || cpuSocket.equals("Unknown")) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.WARNING,
                    "CPU socket could not be determined for this model",
                    "Socket Compatibility"
            );
        }

        // Resolve LGA1151-v1 vs LGA1151-v2 from the motherboard's actual chipset in its name,
        // since the raw jsonl just labels both generations "LGA1151".
        String moboSocket = ChipsetGenerationLookup.resolveSocket(
                motherboard.get().getSocket(), motherboard.get().getName()
        );

        if (moboSocket.equals("LGA1151-Unknown")) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.WARNING,
                    "Could not determine whether this board is LGA1151 v1 (100/200 series) "
                            + "or v2 (300 series) from its name — verify chipset manually before buying",
                    "Socket Compatibility"
            );
        }

        boolean compatible = SocketCompatibilityResolver.isCompatible(cpuSocket, moboSocket);

        if (compatible) {
            boolean exactMatch = cpuSocket.equals(moboSocket);
            String note = exactMatch ? "" : " (backward compatible)";
            return new CompatibilityResult(
                    CompatibilityResult.Status.COMPATIBLE,
                    "CPU socket (" + cpuSocket + ") works with motherboard (" + moboSocket + ")" + note,
                    "Socket Compatibility"
            );
        } else {
            return new CompatibilityResult(
                    CompatibilityResult.Status.INCOMPATIBLE,
                    "CPU needs " + cpuSocket + ", but motherboard is " + moboSocket,
                    "Socket Compatibility"
            );
        }
    }
}