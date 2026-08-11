package com.pccompatchecker.Compatibility.rules;

import com.pccompatchecker.Compatibility.CompatibilityResult;
import com.pccompatchecker.Compatibility.CompatibilityRule;
import com.pccompatchecker.build.Build;
import com.pccompatchecker.Components.Motherboard;
import com.pccompatchecker.Components.RAM;

import java.util.Optional;

public class RamCapacityRule implements CompatibilityRule {

    @Override
    public CompatibilityResult check(Build build) {
        Optional<Motherboard> motherboard = build.getMotherboard();
        Optional<RAM> ram = build.getRam();

        if (motherboard.isEmpty() || ram.isEmpty()) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.NOT_APPLICABLE,
                    "Motherboard or RAM not selected yet",
                    "RAM Capacity"
            );
        }

        Integer maxMemory = motherboard.get().getMaxMemory();
        int ramCapacity = ram.get().getTotalCapacity();

        if (maxMemory == null) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.WARNING,
                    "Motherboard max memory unknown — cannot verify capacity",
                    "RAM Capacity"
            );
        }

        if (ramCapacity > maxMemory) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.INCOMPATIBLE,
                    "RAM (" + ramCapacity + "GB) exceeds motherboard max (" + maxMemory + "GB)",
                    "RAM Capacity"
            );
        }

        return new CompatibilityResult(
                CompatibilityResult.Status.COMPATIBLE,
                "RAM capacity (" + ramCapacity + "GB) fits within motherboard max (" + maxMemory + "GB)",
                "RAM Capacity"
        );
    }
}