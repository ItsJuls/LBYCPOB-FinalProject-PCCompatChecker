package com.pccompatchecker.Compatibility;

import com.pccompatchecker.build.Build;

public interface CompatibilityRule {
    CompatibilityResult check(Build build);
}