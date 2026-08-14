package com.pccompatchecker.controller.filter;

import java.util.function.Predicate;

/**
 * A single checkbox in a filter group, e.g. "AMD" within the "Brand" group.
 */
public record FilterOption<T>(String label, Predicate<T> predicate) {
}
