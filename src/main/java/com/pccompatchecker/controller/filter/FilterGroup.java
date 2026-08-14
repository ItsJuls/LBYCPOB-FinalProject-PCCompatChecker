package com.pccompatchecker.controller.filter;

import java.util.List;

/**
 * A titled group of checkboxes shown in a filter popup, e.g. "Brand" containing
 * "AMD" / "Intel" options. Leaving every checkbox in a group unchecked means
 * that group doesn't restrict the results at all.
 */
public record FilterGroup<T>(String title, List<FilterOption<T>> options) {
}
