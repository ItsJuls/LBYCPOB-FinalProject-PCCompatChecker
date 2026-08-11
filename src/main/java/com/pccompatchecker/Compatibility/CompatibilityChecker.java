package com.pccompatchecker.Compatibility;

import com.pccompatchecker.Compatibility.rules.*;
import com.pccompatchecker.build.Build;

import java.util.ArrayList;
import java.util.List;

public class CompatibilityChecker {

    private final List<CompatibilityRule> rules = new ArrayList<>();

    public CompatibilityChecker() {
        rules.add(new SocketCompatibilityRule());
        rules.add(new FormFactorRule());
        rules.add(new RamCapacityRule());
        rules.add(new CoolerSocketRule());
        rules.add(new RamGenerationRule());
    }

    public List<CompatibilityResult> runAll(Build build) {
        List<CompatibilityResult> results = new ArrayList<>();
        for (CompatibilityRule rule : rules) {
            results.add(rule.check(build));
        }
        return results;
    }

    public boolean hasAnyIncompatibility(List<CompatibilityResult> results) {
        return results.stream()
                .anyMatch(r -> r.getStatus() == CompatibilityResult.Status.INCOMPATIBLE);
    }
}