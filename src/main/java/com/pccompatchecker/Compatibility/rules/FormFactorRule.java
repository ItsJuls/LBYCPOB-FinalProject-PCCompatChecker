package com.pccompatchecker.Compatibility.rules;

import com.pccompatchecker.Compatibility.CompatibilityResult;
import com.pccompatchecker.Compatibility.CompatibilityRule;
import com.pccompatchecker.build.Build;
import com.pccompatchecker.Components.Motherboard;
import com.pccompatchecker.Components.Case;

import java.util.Optional;

public class FormFactorRule implements CompatibilityRule {

    @Override
    public CompatibilityResult check(Build build) {
        Optional<Motherboard> motherboard = build.getMotherboard();
        Optional<Case> pcCase = build.getCase();

        if (motherboard.isEmpty() || pcCase.isEmpty()) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.NOT_APPLICABLE,
                    "Motherboard or case not selected yet",
                    "Form Factor"
            );
        }

        String formFactor = motherboard.get().getFormFactor(); // e.g. "ATX"
        String caseType = pcCase.get().getType(); // e.g. "ATX Mid Tower"

        if (formFactor == null || caseType == null) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.WARNING,
                    "Form factor data unavailable for one of the selected parts",
                    "Form Factor"
            );
        }

        if (caseType.toUpperCase().contains(formFactor.toUpperCase().replace(" ", ""))) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.COMPATIBLE,
                    "Motherboard (" + formFactor + ") fits in case (" + caseType + ")",
                    "Form Factor"
            );
        } else {
            return new CompatibilityResult(
                    CompatibilityResult.Status.WARNING,
                    "Case type (" + caseType + ") may not confirm support for " + formFactor + " — verify manually",
                    "Form Factor"
            );
        }
    }
}