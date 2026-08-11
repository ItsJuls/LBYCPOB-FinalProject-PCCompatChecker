package com.pccompatchecker.Compatibility.rules;

import com.pccompatchecker.build.Build;
import com.pccompatchecker.Compatibility.CompatibilityResult;
import com.pccompatchecker.Compatibility.CompatibilityRule;
import com.pccompatchecker.Components.CPU;
import com.pccompatchecker.Components.Motherboard;

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
        String moboSocket = motherboard.get().getSocket();

        if (cpuSocket.equals("Unknown")) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.WARNING,
                    "CPU socket could not be determined for this model",
                    "Socket Compatibility"
            );
        }

        if (cpuSocket.equals(moboSocket)) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.COMPATIBLE,
                    "CPU socket (" + cpuSocket + ") matches motherboard socket",
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