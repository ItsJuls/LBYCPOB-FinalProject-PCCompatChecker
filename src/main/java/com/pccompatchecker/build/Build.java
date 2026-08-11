package com.pccompatchecker.build;

import com.pccompatchecker.Components.*;

import java.util.Optional;

public class Build {

    private Optional<CPU> cpu = Optional.empty();
    private Optional<Motherboard> motherboard = Optional.empty();
    private Optional<RAM> ram = Optional.empty();
    private Optional<GPU> gpu = Optional.empty();
    private Optional<Storage> storage = Optional.empty();
    private Optional<PSU> psu = Optional.empty();
    private Optional<Case> pcCase = Optional.empty(); // "case" is a Java keyword, can't use as var name
    private Optional<CPUCooler> cpuCooler = Optional.empty();

    public void setCpu(CPU cpu) { this.cpu = Optional.ofNullable(cpu); }
    public void setMotherboard(Motherboard motherboard) { this.motherboard = Optional.ofNullable(motherboard); }
    public void setRam(RAM ram) { this.ram = Optional.ofNullable(ram); }
    public void setGpu(GPU gpu) { this.gpu = Optional.ofNullable(gpu); }
    public void setStorage(Storage storage) { this.storage = Optional.ofNullable(storage); }
    public void setPsu(PSU psu) { this.psu = Optional.ofNullable(psu); }
    public void setCase(Case pcCase) { this.pcCase = Optional.ofNullable(pcCase); }
    public void setCpuCooler(CPUCooler cpuCooler) { this.cpuCooler = Optional.ofNullable(cpuCooler); }

    public Optional<CPU> getCpu() { return cpu; }
    public Optional<Motherboard> getMotherboard() { return motherboard; }
    public Optional<RAM> getRam() { return ram; }
    public Optional<GPU> getGpu() { return gpu; }
    public Optional<Storage> getStorage() { return storage; }
    public Optional<PSU> getPsu() { return psu; }
    public Optional<Case> getCase() { return pcCase; }
    public Optional<CPUCooler> getCpuCooler() { return cpuCooler; }

    public double getTotalPrice() {
        double total = 0;
        total += priceOf(cpu);
        total += priceOf(motherboard);
        total += priceOf(ram);
        total += priceOf(gpu);
        total += priceOf(storage);
        total += priceOf(psu);
        total += priceOf(pcCase);
        total += priceOf(cpuCooler);
        return total;
    }

    private double priceOf(Optional<? extends Component> component) {
        if (component.isEmpty()) return 0.0;
        Optional<Double> price = component.get().getPrice();
        return price.orElse(0.0);
    }
}