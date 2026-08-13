package com.pccompatchecker.controller;

import com.pccompatchecker.Components.CPU;
import com.pccompatchecker.Components.GPU;
import com.pccompatchecker.Components.Motherboard;
import com.pccompatchecker.Components.RAM;
import com.pccompatchecker.Components.Storage;
import com.pccompatchecker.Components.PSU;
import com.pccompatchecker.Components.Case;

import com.pccompatchecker.Compatibility.CompatibilityChecker;
import com.pccompatchecker.Compatibility.CompatibilityResult;
import com.pccompatchecker.build.Build;

import java.util.List;

import com.pccompatchecker.Components.*;
import com.pccompatchecker.repository.ComponentRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.event.ActionEvent;

public class MainController {

    // PC component dropdowns
    @FXML
    private ComboBox<CPU> cpuCBox;

    @FXML
    private ComboBox<Motherboard> moboCBox;

    @FXML
    private ComboBox<RAM> ramCBox;

    @FXML
    private ComboBox<GPU> gpuCBox;

    @FXML
    private ComboBox<Storage> storageCBox;

    @FXML
    private ComboBox<CPUCooler> coolerCBox;

    @FXML
    private Slider budgetSlider;

    @FXML
    private Label budgetValueLabel;

    @FXML
    private ComboBox<PSU> psuCBox;

    @FXML
    private ComboBox<Case> caseCBox;

    @FXML
    private void compatibilityCheck(ActionEvent event) {
        // Get the components selected by the user.
        CPU cpu = cpuCBox.getValue();
        Motherboard motherboard = moboCBox.getValue();
        RAM ram = ramCBox.getValue();
        GPU gpu = gpuCBox.getValue();
        Storage storage = storageCBox.getValue();
        CPUCooler cooler = coolerCBox.getValue();
        PSU psu = psuCBox.getValue();
        Case pcCase = caseCBox.getValue();

        // Create a Build using the selected components.
        Build build = new Build();

        build.setCpu(cpu);
        build.setMotherboard(motherboard);
        build.setRam(ram);
        build.setGpu(gpu);
        build.setStorage(storage);
        build.setCpuCooler(cooler);

        // Run the existing backend compatibility rules.
        CompatibilityChecker checker = new CompatibilityChecker();
        List<CompatibilityResult> results = checker.runAll(build);

        // Print the results for testing.
        for (CompatibilityResult result : results) {
            System.out.println(
                    result.getStatus() + ": " +
                            result.getMessage()
            );
        }
    }

    // Loads component data from the JSONL files
    private final ComponentRepository repository = new ComponentRepository();

    @FXML
    public void initialize() {

        cpuCBox.getItems().addAll(repository.getCpus());
        moboCBox.getItems().addAll(repository.getMotherboards());
        ramCBox.getItems().addAll(repository.getRams());
        gpuCBox.getItems().addAll(repository.getGpus());
        storageCBox.getItems().addAll(repository.getStorages());
        coolerCBox.getItems().addAll(repository.getCpuCoolers());
        psuCBox.getItems().addAll(repository.getPsus());
        caseCBox.getItems().addAll(repository.getCases());

        budgetSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            budgetValueLabel.setText(String.format("₱%,.0f", newValue.doubleValue())
            );
        });
    }
}