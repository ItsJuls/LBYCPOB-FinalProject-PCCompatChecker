package com.pccompatchecker.Compatibility;

import com.pccompatchecker.build.Build;

import java.util.ArrayList;
import java.util.List;

public class CompatibilityChecker {

    private final List<CompatibilityRule> rules = new ArrayList<>();

    public CompatibilityChecker() {
        rules.add(new com.pccompatchecker.Compatibility.rules.SocketCompatibilityRule());
        rules.add(new com.pccompatchecker.Compatibility.rules.FormFactorRule());
        rules.add(new com.pccompatchecker.Compatibility.rules.RamCapacityRule());
        rules.add(new com.pccompatchecker.Compatibility.rules.CoolerSocketRule());
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