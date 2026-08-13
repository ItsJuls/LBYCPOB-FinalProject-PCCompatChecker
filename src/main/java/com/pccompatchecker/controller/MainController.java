package com.pccompatchecker.controller;

import com.pccompatchecker.Components.*;
import com.pccompatchecker.repository.ComponentRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

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
    private Slider budgetSlider;

    @FXML
    private Label budgetValueLabel;

    // Loads component data from the JSONL files
    private final ComponentRepository repository = new ComponentRepository();

    @FXML
    public void initialize() {

        cpuCBox.getItems().addAll(repository.getCpus());
        moboCBox.getItems().addAll(repository.getMotherboards());
        ramCBox.getItems().addAll(repository.getRams());
        gpuCBox.getItems().addAll(repository.getGpus());
        storageCBox.getItems().addAll(repository.getStorages());

        budgetSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            budgetValueLabel.setText(String.format("₱%,.0f", newValue.doubleValue()));
        });
    }
}