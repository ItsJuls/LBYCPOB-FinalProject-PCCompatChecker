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

import java.util.Comparator;
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
import javafx.scene.control.TextField;
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
    private Label totalSpentLabel;

    @FXML
    private Label remainingBudgetLabel;

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
    private TextField budgetInput;

    @FXML
    private Button clearBuildButton;

    @FXML
    private Label componentCountLabel;

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

    @FXML
    private void clearBuild(ActionEvent event) {

        cpuCBox.setValue(null);
        moboCBox.setValue(null);
        ramCBox.setValue(null);
        gpuCBox.setValue(null);
        storageCBox.setValue(null);
        coolerCBox.setValue(null);
        psuCBox.setValue(null);
        caseCBox.setValue(null);

        resultsTextArea.clear();

        updateBudgetDisplay();
    }

    private void updateBudgetDisplay() {
        double budget = budgetSlider.getValue();
        double spent = 0;

        if (cpuCBox.getValue() != null)
            spent += cpuCBox.getValue().getPricePhp().orElse(0.0);

        if (moboCBox.getValue() != null)
            spent += moboCBox.getValue().getPricePhp().orElse(0.0);

        if (ramCBox.getValue() != null)
            spent += ramCBox.getValue().getPricePhp().orElse(0.0);

        if (gpuCBox.getValue() != null)
            spent += gpuCBox.getValue().getPricePhp().orElse(0.0);

        if (storageCBox.getValue() != null)
            spent += storageCBox.getValue().getPricePhp().orElse(0.0);

        if (coolerCBox.getValue() != null)
            spent += coolerCBox.getValue().getPricePhp().orElse(0.0);

        if (psuCBox.getValue() != null)
            spent += psuCBox.getValue().getPricePhp().orElse(0.0);

        if (caseCBox.getValue() != null)
            spent += caseCBox.getValue().getPricePhp().orElse(0.0);

        double remaining = budget - spent;

        budgetValueLabel.setText(
                String.format("Budget: ₱%,.0f", budget)
        );

        totalSpentLabel.setText(
                String.format("Total Spent: ₱%,.0f", spent)
        );

        remainingBudgetLabel.setText(
                String.format("Remaining: ₱%,.0f", remaining)
        );
    }

    private void updateComponentCount() {

        int count = 0;

        if (cpuCBox.getValue() != null)
            count++;

        if (moboCBox.getValue() != null)
            count++;

        if (ramCBox.getValue() != null)
            count++;

        if (gpuCBox.getValue() != null)
            count++;

        if (storageCBox.getValue() != null)
            count++;

        if (coolerCBox.getValue() != null)
            count++;

        if (psuCBox.getValue() != null)
            count++;

        if (caseCBox.getValue() != null)
            count++;

        componentCountLabel.setText(
                "Components Selected: " + count + " / 8"
        );
    }

    // Loads component data from the JSONL files
    private final ComponentRepository repository = new ComponentRepository();

    @FXML
    public void initialize() {

        Thread forexThread = new Thread(Component::updateExchangeRate);
        forexThread.setDaemon(true);
        forexThread.start();

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

        budgetSlider.valueProperty().addListener(
                (obs, oldValue, newValue) -> {updateBudgetDisplay(); updateComponentCount();}
        );
        cpuCBox.valueProperty().addListener(
                (obs, oldValue, newValue) -> {updateBudgetDisplay(); updateComponentCount();}
        );
        moboCBox.valueProperty().addListener(
                (obs, oldValue, newValue) -> {updateBudgetDisplay(); updateComponentCount();}
        );
        ramCBox.valueProperty().addListener(
                (obs, oldValue, newValue) -> {updateBudgetDisplay(); updateComponentCount();}
        );
        gpuCBox.valueProperty().addListener(
                (obs, oldValue, newValue) -> {updateBudgetDisplay(); updateComponentCount();}
        );
        storageCBox.valueProperty().addListener(
                (obs, oldValue, newValue) -> {updateBudgetDisplay(); updateComponentCount();}
        );
        coolerCBox.valueProperty().addListener(
                (obs, oldValue, newValue) -> {updateBudgetDisplay(); updateComponentCount();}
        );
        psuCBox.valueProperty().addListener(
                (obs, oldValue, newValue) -> {updateBudgetDisplay(); updateComponentCount();}
        );
        caseCBox.valueProperty().addListener(
                (obs, oldValue, newValue) -> {updateBudgetDisplay(); updateComponentCount();}
        );
        updateBudgetDisplay();

        setupFilter(cpuCBox, cpuFilterButton, allCpus, ComponentFilters.cpu());
        setupFilter(moboCBox, moboFilterButton, allMotherboards, ComponentFilters.motherboard());
        setupFilter(ramCBox, ramFilterButton, allRams, ComponentFilters.ram());
        setupFilter(gpuCBox, gpuFilterButton, allGpus, ComponentFilters.gpu());
        setupFilter(storageCBox, storageFilterButton, allStorages, ComponentFilters.storage());
        setupFilter(coolerCBox, coolerFilterButton, allCoolers, ComponentFilters.cpuCooler());
        setupFilter(psuCBox, psuFilterButton, allPsus, ComponentFilters.psu());
        setupFilter(caseCBox, caseFilterButton, allCases, ComponentFilters.pcCase());

        budgetInput.setOnAction(event -> {
            try {
                double budget = Double.parseDouble(budgetInput.getText());

                if (budget >= budgetSlider.getMin() &&
                        budget <= budgetSlider.getMax()) {

                    budgetSlider.setValue(budget);
                }

            } catch (NumberFormatException e) {
                budgetInput.setText(
                        String.format("%.0f", budgetSlider.getValue())
                );
            }
        });
    }

    /**
     * Wires a dropdown's "Filter" button to a checklist popup built from the
     * given filter groups, plus a shared price range slider and sort dropdown.
     * Any change re-filters and re-sorts the master list and pushes the
     * result into the combo box's items; the button label shows how many
     * filters (checkboxes + price range) are currently active.
     */
    private <T extends Component> void setupFilter(SearchableComboBox<T> comboBox, Button filterButton,
                                                     List<T> masterList, List<FilterGroup<T>> groups) {

        double priceMin = masterList.stream()
                .flatMap(t -> t.getPricePhp().stream())
                .min(Double::compareTo).orElse(0.0);
        double priceMax = masterList.stream()
                .flatMap(t -> t.getPricePhp().stream())
                .max(Double::compareTo).orElse(0.0);

        FilterPopup<T> popup = new FilterPopup<>(
                groups,
                priceMin, priceMax,
                (Predicate<T> predicate, FilterPopup.SortOption sortOption) -> {
                    T previousSelection = comboBox.getValue();

                    List<T> filtered = masterList.stream()
                            .filter(predicate)
                            .collect(Collectors.toList());

                    Comparator<T> comparator = switch (sortOption) {
                        case PRICE_LOW_HIGH -> Comparator.comparingDouble(
                                (T t) -> t.getPricePhp().orElse(Double.MAX_VALUE));
                        case PRICE_HIGH_LOW -> Comparator.comparingDouble(
                                (T t) -> t.getPricePhp().orElse(Double.MIN_VALUE)).reversed();
                        case NAME_A_Z -> Comparator.comparing(T::getName, String.CASE_INSENSITIVE_ORDER);
                        default -> null;
                    };
                    if (comparator != null) {
                        filtered.sort(comparator);
                    }

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
