package com.pccompatchecker.Compatibility.rules;

import com.pccompatchecker.Compatibility.CompatibilityResult;
import com.pccompatchecker.Compatibility.CompatibilityRule;
import com.pccompatchecker.build.Build;
import com.pccompatchecker.Components.CPU;
import com.pccompatchecker.Components.GPU;
import com.pccompatchecker.Components.PSU;
import com.pccompatchecker.util.GpuPowerLookup;

import java.util.Optional;

/**
 * Estimates total system power draw (CPU + GPU + baseline overhead for
 * motherboard/RAM/storage/fans) and checks it against the selected PSU's
 * wattage, with a headroom margin for efficiency/transient spikes.
 *
 * If no GPU is selected, the build is assumed to run on the CPU's
 * integrated graphics, so the GPU's contribution to the estimate is 0 —
 * a GPU-less build needs meaningfully less PSU wattage than one with a
 * discrete card.
 */
public class PowerRequirementRule implements CompatibilityRule {


    private static final int BASE_SYSTEM_OVERHEAD_WATTS = 80;


    private static final double RECOMMENDED_HEADROOM = 1.3;

    @Override
    public CompatibilityResult check(Build build) {
        Optional<CPU> cpuOpt = build.getCpu();
        Optional<PSU> psuOpt = build.getPsu();

        if (cpuOpt.isEmpty() || psuOpt.isEmpty()) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.NOT_APPLICABLE,
                    "CPU and PSU must both be selected to estimate power requirements",
                    "Power Requirement"
            );
        }

        CPU cpu = cpuOpt.get();
        PSU psu = psuOpt.get();
        Optional<GPU> gpuOpt = build.getGpu();

        double cpuWatts = cpu.getTdp();
        double gpuWatts = 0;
        String gpuNote;

        if (gpuOpt.isPresent()) {
            GPU gpu = gpuOpt.get();
            Integer exact = GpuPowerLookup.getExactWatts(gpu.getChipset());
            if (exact != null) {
                gpuWatts = exact;
                gpuNote = gpu.getChipset() + " (~" + exact + "W)";
            } else {
                gpuWatts = GpuPowerLookup.estimateWattsFromMemory(gpu.getMemory());
                gpuNote = gpu.getChipset() + " (~" + (int) gpuWatts + "W, estimated from VRAM — unlisted chipset)";
            }
        } else {
            gpuNote = "none selected — assuming CPU integrated graphics";
        }

        double estimatedLoad = cpuWatts + gpuWatts + BASE_SYSTEM_OVERHEAD_WATTS;
        double recommendedWatts = estimatedLoad * RECOMMENDED_HEADROOM;
        int psuWatts = psu.getWattage();

        String breakdown = String.format(
                "Estimated draw: %.0fW (CPU %.0fW + GPU %s + %dW baseline). PSU: %dW.",
                estimatedLoad, cpuWatts, gpuNote,
                BASE_SYSTEM_OVERHEAD_WATTS, psuWatts
        );

        if (psuWatts < estimatedLoad) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.INCOMPATIBLE,
                    "PSU wattage is too low for the estimated system draw. " + breakdown,
                    "Power Requirement"
            );
        }

        if (psuWatts < recommendedWatts) {
            return new CompatibilityResult(
                    CompatibilityResult.Status.WARNING,
                    "PSU covers the estimated draw but has little headroom (recommended ~"
                            + Math.round(recommendedWatts) + "W). " + breakdown,
                    "Power Requirement"
            );
        }

        return new CompatibilityResult(
                CompatibilityResult.Status.COMPATIBLE,
                "PSU wattage comfortably covers the estimated system draw. " + breakdown,
                "Power Requirement"
        );
    }
}
