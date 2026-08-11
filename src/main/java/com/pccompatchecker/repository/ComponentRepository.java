package com.pccompatchecker.repository;

import com.pccompatchecker.Components.*;

import java.util.List;

public class ComponentRepository {

    private final List<CPU> cpus;
    private final List<Motherboard> motherboards;
    private final List<RAM> rams;
    private final List<GPU> gpus;
    private final List<Storage> storages;
    private final List<PSU> psus;
    private final List<Case> cases;
    private final List<CPUCooler> cpuCoolers;

    public ComponentRepository() {
        this.cpus = JsonlLoader.load("/parts/jsonl/cpu.jsonl", CPU.class);
        this.motherboards = JsonlLoader.load("/parts/jsonl/motherboard.jsonl", Motherboard.class);
        this.rams = JsonlLoader.load("/parts/jsonl/memory.jsonl", RAM.class);
        this.gpus = JsonlLoader.load("/parts/jsonl/video-card.jsonl", GPU.class);
        this.storages = JsonlLoader.load("/parts/jsonl/internal-hard-drive.jsonl", Storage.class);
        this.psus = JsonlLoader.load("/parts/jsonl/power-supply.jsonl", PSU.class);
        this.cases = JsonlLoader.load("/parts/jsonl/case.jsonl", Case.class);
        this.cpuCoolers = JsonlLoader.load("/parts/jsonl/cpu-cooler.jsonl", CPUCooler.class);
    }

    public List<CPU> getCpus() { return cpus; }
    public List<Motherboard> getMotherboards() { return motherboards; }
    public List<RAM> getRams() { return rams; }
    public List<GPU> getGpus() { return gpus; }
    public List<Storage> getStorages() { return storages; }
    public List<PSU> getPsus() { return psus; }
    public List<Case> getCases() { return cases; }
    public List<CPUCooler> getCpuCoolers() { return cpuCoolers; }
}