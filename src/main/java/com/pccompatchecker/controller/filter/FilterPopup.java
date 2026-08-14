package com.pccompatchecker.controller.filter;

import com.pccompatchecker.Components.Component;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Popup;
import org.controlsfx.control.RangeSlider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

/**
 * A small popup showing:
 *  - a price range slider (applies to every category, since price lives on
 *    the shared Component base class),
 *  - one or more titled checkbox groups (category-specific, e.g. Brand),
 *  - a sort dropdown,
 *  - and a Reset button that clears everything back to "no filter, no sort".
 *
 * Checkbox filtering is live — every toggle recomputes the combined predicate
 * and hands it (plus the current sort comparator) to the caller. Checkboxes
 * within a group are OR'd together; groups are AND'd with each other. An
 * empty (all-unchecked) group doesn't restrict results. The price slider only
 * restricts results once the user actually moves a thumb — parts with an
 * unknown price are excluded once it's active, since we can't confirm they're
 * in range.
 */
public class FilterPopup<T extends Component> {

    public enum SortOption {
        DEFAULT("Default order"),
        PRICE_LOW_HIGH("Price: Low to High"),
        PRICE_HIGH_LOW("Price: High to Low"),
        NAME_A_Z("Name: A-Z");

        private final String label;
        SortOption(String label) { this.label = label; }
        @Override public String toString() { return label; }
    }

    private final Popup popup = new Popup();
    private final List<CheckBox> allCheckBoxes = new ArrayList<>();
    private final List<FilterGroup<T>> groups;
    private final BiConsumer<Predicate<T>, SortOption> onFilterChanged;
    private final IntConsumer onActiveCountChanged;

    private RangeSlider priceSlider;
    private ComboBox<SortOption> sortComboBox;
    private boolean priceSliderTouched = false;
    private final double priceMin;
    private final double priceMax;

    /**
     * @param groups          category-specific checkbox groups
     * @param priceMinPhp     lowest known PHP price in the master list (for the slider bounds)
     * @param priceMaxPhp     highest known PHP price in the master list
     * @param onFilterChanged called with (combinedPredicate, sortOption) whenever anything changes
     * @param onActiveCountChanged called with how many checkboxes + the price slider are currently active
     */
    public FilterPopup(List<FilterGroup<T>> groups,
                        double priceMinPhp, double priceMaxPhp,
                        BiConsumer<Predicate<T>, SortOption> onFilterChanged,
                        IntConsumer onActiveCountChanged) {
        this.groups = groups;
        this.priceMin = Math.floor(priceMinPhp);
        this.priceMax = Math.max(this.priceMin + 1, Math.ceil(priceMaxPhp));
        this.onFilterChanged = onFilterChanged;
        this.onActiveCountChanged = onActiveCountChanged;
        build();
    }

    private void build() {
        VBox root = new VBox(8);
        root.setPadding(new Insets(10));
        // Force text color explicitly — on systems with a dark OS theme,
        // JavaFX's default control text can resolve to white, which would
        // otherwise vanish against this popup's white card background.
        root.setStyle("-fx-background-color: white; -fx-border-color: #b0b0b0; "
                + "-fx-border-width: 1; -fx-text-fill: #1a1a1a; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0, 0, 2);");
        root.setPrefWidth(260);

        // ---- Price range ----
        Label priceTitle = new Label("Price (₱)");
        priceTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        root.getChildren().add(priceTitle);

        priceSlider = new RangeSlider(priceMin, priceMax, priceMin, priceMax);
        priceSlider.setShowTickLabels(false);
        priceSlider.setShowTickMarks(false);

        Label priceRangeLabel = new Label(formatPeso(priceMin) + " - " + formatPeso(priceMax));
        priceRangeLabel.setStyle("-fx-text-fill: #1a1a1a;");
        Runnable updatePriceLabel = () -> priceRangeLabel.setText(
                formatPeso(priceSlider.getLowValue()) + " - " + formatPeso(priceSlider.getHighValue()));

        priceSlider.lowValueProperty().addListener((obs, oldVal, newVal) -> {
            priceSliderTouched = true;
            updatePriceLabel.run();
            recompute();
        });
        priceSlider.highValueProperty().addListener((obs, oldVal, newVal) -> {
            priceSliderTouched = true;
            updatePriceLabel.run();
            recompute();
        });

        root.getChildren().addAll(priceSlider, priceRangeLabel, new Separator());

        // ---- Sort ----
        Label sortTitle = new Label("Sort by");
        sortTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
        sortComboBox = new ComboBox<>();
        sortComboBox.getItems().addAll(SortOption.values());
        sortComboBox.setValue(SortOption.DEFAULT);
        sortComboBox.setMaxWidth(Double.MAX_VALUE);
        sortComboBox.valueProperty().addListener((obs, oldVal, newVal) -> recompute());
        root.getChildren().addAll(sortTitle, sortComboBox, new Separator());

        // ---- Checkbox groups ----
        for (FilterGroup<T> group : groups) {
            Label title = new Label(group.title());
            title.setStyle("-fx-font-weight: bold; -fx-text-fill: #1a1a1a;");
            root.getChildren().add(title);

            for (FilterOption<T> option : group.options()) {
                CheckBox checkBox = new CheckBox(option.label());
                checkBox.setStyle("-fx-text-fill: #1a1a1a;");
                checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> recompute());
                checkBox.getProperties().put("option", option);
                checkBox.getProperties().put("group", group);
                allCheckBoxes.add(checkBox);
                root.getChildren().add(checkBox);
            }

            root.getChildren().add(new Separator());
        }

        Button resetButton = new Button("Reset filters");
        resetButton.setOnAction(e -> reset());

        HBox buttonRow = new HBox(resetButton);
        root.getChildren().add(buttonRow);

        // Wrap in a ScrollPane so long checkbox lists (e.g. CPU's Brand +
        // Socket + TDP + Microarchitecture + Graphics groups) don't get cut
        // off at the bottom of the screen — cap the visible height and let
        // the rest scroll.
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxHeight(500);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        popup.getContent().add(scrollPane);
        popup.setAutoHide(true);
    }

    private String formatPeso(double value) {
        return String.format("₱%,.0f", value);
    }

    @SuppressWarnings("unchecked")
    private void recompute() {
        Predicate<T> combined = t -> true;
        int activeCount = 0;

        for (FilterGroup<T> group : groups) {
            List<Predicate<T>> checkedPredicates = new ArrayList<>();
            for (CheckBox checkBox : allCheckBoxes) {
                if (checkBox.getProperties().get("group") != group) continue;
                if (checkBox.isSelected()) {
                    FilterOption<T> option = (FilterOption<T>) checkBox.getProperties().get("option");
                    checkedPredicates.add(option.predicate());
                    activeCount++;
                }
            }
            if (!checkedPredicates.isEmpty()) {
                Predicate<T> groupPredicate = checkedPredicates.stream()
                        .reduce(Predicate::or)
                        .orElse(t -> true);
                combined = combined.and(groupPredicate);
            }
        }

        if (priceSliderTouched) {
            double low = priceSlider.getLowValue();
            double high = priceSlider.getHighValue();
            combined = combined.and(t -> t.getPricePhp().map(p -> p >= low && p <= high).orElse(false));
            activeCount++;
        }

        onFilterChanged.accept(combined, sortComboBox.getValue());
        onActiveCountChanged.accept(activeCount);
    }

    private void reset() {
        for (CheckBox checkBox : allCheckBoxes) {
            checkBox.setSelected(false);
        }
        // Setting these triggers the value listeners above, which mark the
        // slider as "touched" — so reset priceSliderTouched again afterwards.
        priceSlider.setLowValue(priceMin);
        priceSlider.setHighValue(priceMax);
        priceSliderTouched = false;
        sortComboBox.setValue(SortOption.DEFAULT);
        recompute();
    }

    public void toggle(Node anchor) {
        if (popup.isShowing()) {
            popup.hide();
            return;
        }
        Bounds bounds = anchor.localToScreen(anchor.getBoundsInLocal());
        popup.show(anchor, bounds.getMinX(), bounds.getMaxY() + 4);
    }
}
