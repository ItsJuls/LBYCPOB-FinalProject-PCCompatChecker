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
import com.pccompatchecker.controller.filter.ComponentFilters;
import com.pccompatchecker.controller.filter.FilterGroup;
import com.pccompatchecker.controller.filter.FilterPopup;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.pccompatchecker.Components.*;
import com.pccompatchecker.repository.ComponentRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import org.controlsfx.control.SearchableComboBox;

public class MainController {

    // PC component dropdowns
    @FXML
    private SearchableComboBox<CPU> cpuCBox;
    @FXML
    private Button cpuFilterButton;

    @FXML
    private SearchableComboBox<Motherboard> moboCBox;
    @FXML
    private Button moboFilterButton;

    @FXML
    private SearchableComboBox<RAM> ramCBox;
    @FXML
    private Button ramFilterButton;

    @FXML
    private SearchableComboBox<GPU> gpuCBox;
    @FXML
    private Button gpuFilterButton;

    @FXML
    private SearchableComboBox<Storage> storageCBox;
    @FXML
    private Button storageFilterButton;

    @FXML
    private SearchableComboBox<CPUCooler> coolerCBox;
    @FXML
    private Button coolerFilterButton;

    @FXML
    private Slider budgetSlider;

    @FXML
    private Label budgetValueLabel;

    @FXML
    private SearchableComboBox<PSU> psuCBox;
    @FXML
    private Button psuFilterButton;

    @FXML
    private SearchableComboBox<Case> caseCBox;
    @FXML
    private Button caseFilterButton;

    @FXML
    private TextArea resultsTextArea;

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
        build.setPsu(psu);
        build.setCase(pcCase);

        // Run the existing backend compatibility rules.
        CompatibilityChecker checker = new CompatibilityChecker();
        List<CompatibilityResult> results = checker.runAll(build);

        // Print the results for testing.
        resultsTextArea.clear();

        for (CompatibilityResult result : results) {
            String symbol = switch (result.getStatus()) {
                case COMPATIBLE -> "✓";
                case WARNING -> "⚠";
                case INCOMPATIBLE -> "✗";
                default -> "•";
            };

            resultsTextArea.appendText(
                    symbol + " " + result.getStatus() + "\n" +
                            "  " + result.getMessage() + "\n\n"
            );
        }
    }

    // Loads component data from the JSONL files
    private final ComponentRepository repository = new ComponentRepository();

    @FXML
    public void initialize() {

        List<CPU> allCpus = repository.getCpus();
        List<Motherboard> allMotherboards = repository.getMotherboards();
        List<RAM> allRams = repository.getRams();
        List<GPU> allGpus = repository.getGpus();
        List<Storage> allStorages = repository.getStorages();
        List<CPUCooler> allCoolers = repository.getCpuCoolers();
        List<PSU> allPsus = repository.getPsus();
        List<Case> allCases = repository.getCases();

        cpuCBox.getItems().addAll(allCpus);
        moboCBox.getItems().addAll(allMotherboards);
        ramCBox.getItems().addAll(allRams);
        gpuCBox.getItems().addAll(allGpus);
        storageCBox.getItems().addAll(allStorages);
        coolerCBox.getItems().addAll(allCoolers);
        psuCBox.getItems().addAll(allPsus);
        caseCBox.getItems().addAll(allCases);

        setupFilter(cpuCBox, cpuFilterButton, allCpus, ComponentFilters.cpu());
        setupFilter(moboCBox, moboFilterButton, allMotherboards, ComponentFilters.motherboard());
        setupFilter(ramCBox, ramFilterButton, allRams, ComponentFilters.ram());
        setupFilter(gpuCBox, gpuFilterButton, allGpus, ComponentFilters.gpu());
        setupFilter(storageCBox, storageFilterButton, allStorages, ComponentFilters.storage());
        setupFilter(coolerCBox, coolerFilterButton, allCoolers, ComponentFilters.cpuCooler());
        setupFilter(psuCBox, psuFilterButton, allPsus, ComponentFilters.psu());
        setupFilter(caseCBox, caseFilterButton, allCases, ComponentFilters.pcCase());

        budgetSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            budgetValueLabel.setText(String.format("₱%,.0f", newValue.doubleValue())
            );
        });
    }

    /**
     * Wires a dropdown's "Filter" button to a checklist popup built from the
     * given filter groups. Toggling a checkbox re-filters the master list and
     * pushes the result into the combo box's items; the button label shows
     * how many filters are currently active.
     */
    private <T> void setupFilter(SearchableComboBox<T> comboBox, Button filterButton,
                                  List<T> masterList, List<FilterGroup<T>> groups) {

        FilterPopup<T> popup = new FilterPopup<>(
                groups,
                (Predicate<T> predicate) -> {
                    T previousSelection = comboBox.getValue();
                    List<T> filtered = masterList.stream()
                            .filter(predicate)
                            .collect(Collectors.toList());
                    comboBox.getItems().setAll(filtered);
                    if (previousSelection != null && filtered.contains(previousSelection)) {
                        comboBox.setValue(previousSelection);
                    }
                },
                activeCount -> filterButton.setText(activeCount == 0 ? "Filter" : "Filter (" + activeCount + ")")
        );

        filterButton.setOnAction(e -> popup.toggle(filterButton));
    }
}
