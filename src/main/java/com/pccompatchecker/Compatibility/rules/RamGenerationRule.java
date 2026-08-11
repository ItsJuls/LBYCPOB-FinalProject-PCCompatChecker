package com.pccompatchecker.Compatibility.rules;

import com.pccompatchecker.Compatibility.CompatibilityResult;
import com.pccompatchecker.Compatibility.CompatibilityRule;
import com.pccompatchecker.build.Build;
import com.pccompatchecker.Components.Motherboard;
import com.pccompatchecker.Components.RAM;
import com.pccompatchecker.util.RamGenerationLookup;

import java.util.Optional;

public class RamGenerationRule implements CompatibilityRule {

    @Override
    public CompatibilityResult check(Build build) {
        Optional<Motherboard> motherboard = build.getMotherboard();
        Optional<RAM> ram = build.getRam();

        if (motherboard.isEmpty() || ram.isEmpty()) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.NOT_APPLICABLE,
                    "Motherboard or RAM not selected yet",
                    "RAM Generation"
            );
        }

        String supported = RamGenerationLookup.getSupportedGeneration(
                motherboard.get().getSocket(), motherboard.get().getName()
        );
        String ramGen = "DDR" + ram.get().getGeneration();

        if (supported.startsWith("Unknown")) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.WARNING,
                    "Could not determine motherboard's supported RAM generation",
                    "RAM Generation"
            );
        }

        boolean inferred = supported.contains("inferred");
        String cleanSupported = supported.replace(" (inferred — verify manually)", "");

        if (!cleanSupported.equals(ramGen)) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.INCOMPATIBLE,
                    "RAM is " + ramGen + ", but motherboard supports " + cleanSupported,
                    "RAM Generation"
            );
        }

        if (inferred) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.WARNING,
                    ramGen + " likely supported, but motherboard listing doesn't explicitly confirm — verify manually",
                    "RAM Generation"
            );
        }

        return new CompatibilityResult(
                CompatibilityResult.Status.COMPATIBLE,
                "RAM generation (" + ramGen + ") matches motherboard support",
                "RAM Generation"
        );
    }
}