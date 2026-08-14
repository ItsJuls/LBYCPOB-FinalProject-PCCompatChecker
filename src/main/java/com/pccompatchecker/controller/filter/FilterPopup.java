package com.pccompatchecker.controller.filter;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

/**
 * A small popup showing one or more titled checkbox groups. Filtering is live —
 * every checkbox toggle recomputes the combined predicate and hands it to the
 * caller. Checkboxes within a group are OR'd together; groups are AND'd with
 * each other. An empty (all-unchecked) group doesn't restrict results.
 */
public class FilterPopup<T> {

    private final Popup popup = new Popup();
    private final List<CheckBox> allCheckBoxes = new ArrayList<>();
    private final List<FilterGroup<T>> groups;
    private final Consumer<Predicate<T>> onFilterChanged;
    private final IntConsumer onActiveCountChanged;

    public FilterPopup(List<FilterGroup<T>> groups,
                        Consumer<Predicate<T>> onFilterChanged,
                        IntConsumer onActiveCountChanged) {
        this.groups = groups;
        this.onFilterChanged = onFilterChanged;
        this.onActiveCountChanged = onActiveCountChanged;
        build();
    }

    private void build() {
        VBox root = new VBox(8);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: white; -fx-border-color: #b0b0b0; "
                + "-fx-border-width: 1; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 8, 0, 0, 2);");

        for (FilterGroup<T> group : groups) {
            Label title = new Label(group.title());
            title.setStyle("-fx-font-weight: bold;");
            root.getChildren().add(title);

            for (FilterOption<T> option : group.options()) {
                CheckBox checkBox = new CheckBox(option.label());
                checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> recompute());
                checkBox.getProperties().put("option", option);
                checkBox.getProperties().put("group", group);
                allCheckBoxes.add(checkBox);
                root.getChildren().add(checkBox);
            }

            root.getChildren().add(new Separator());
        }

        Button clearButton = new Button("Clear filters");
        clearButton.setOnAction(e -> clear());

        HBox buttonRow = new HBox(clearButton);
        root.getChildren().add(buttonRow);

        popup.getContent().add(root);
        popup.setAutoHide(true);
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

        onFilterChanged.accept(combined);
        onActiveCountChanged.accept(activeCount);
    }

    private void clear() {
        for (CheckBox checkBox : allCheckBoxes) {
            checkBox.setSelected(false);
        }
        // Listeners fire recompute() for each change above.
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
