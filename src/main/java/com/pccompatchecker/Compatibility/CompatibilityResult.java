package com.pccompatchecker.Compatibility;

public class CompatibilityResult {

    public enum Status {
        COMPATIBLE,
        WARNING,
        INCOMPATIBLE,
        NOT_APPLICABLE // e.g. GPU rule when no GPU is selected
    }

    private final Status status;
    private final String message;
    private final String ruleName;

    public CompatibilityResult(Status status, String message, String ruleName) {
        this.status = status;
        this.message = message;
        this.ruleName = ruleName;
    }

    public Status getStatus() { return status; }
    public String getMessage() { return message; }
    public String getRuleName() { return ruleName; }

    @Override
    public String toString() {
        return "[" + status + "] " + ruleName + ": " + message;
    }
}