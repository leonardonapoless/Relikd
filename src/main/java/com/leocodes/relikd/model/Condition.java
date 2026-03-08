package com.leocodes.relikd.model;

public enum Condition {
    MINT("Mint", "#2ecc71"),
    EXCELLENT("Excellent", "#27ae60"),
    GOOD("Good", "#f39c12"),
    FAIR("Fair", "#e67e22"),
    PARTS("Parts Only", "#e74c3c");

    private final String label;
    private final String badgeColor;

    Condition(String label, String badgeColor) {
        this.label = label;
        this.badgeColor = badgeColor;
    }

    public String getLabel() {
        return label;
    }

    public String getBadgeColor() {
        return badgeColor;
    }
}
