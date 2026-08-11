package com.pccompatchecker.Compatibility.rules;

import com.pccompatchecker.Compatibility.CompatibilityResult;
import com.pccompatchecker.Compatibility.CompatibilityRule;
import com.pccompatchecker.build.Build;
import com.pccompatchecker.Components.CPUCooler;
import com.pccompatchecker.Components.CPU;

import java.util.Optional;

public class CoolerSocketRule implements CompatibilityRule {

    @Override
    public CompatibilityResult check(Build build) {
        Optional<CPUCooler> cooler = build.getCpuCooler();
        Optional<CPU> cpu = build.getCpu();

        if (cooler.isEmpty() || cpu.isEmpty()) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.NOT_APPLICABLE,
                    "CPU or cooler not selected yet",
                    "Cooler Socket Support"
            );
        }

        // No socket-support data exists in the current dataset for coolers.
        return new CompatibilityResult(
                CompatibilityResult.Status.WARNING,
                "Cooler socket support could not be verified — check manufacturer specs manually",
                "Cooler Socket Support"
        );
    }
}