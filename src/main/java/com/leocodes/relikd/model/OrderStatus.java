package com.leocodes.relikd.model;

public enum OrderStatus {
    PENDING("Pending", "#f39c12"),
    CONFIRMED("Confirmed", "#3498db"),
    SHIPPED("Shipped", "#9b59b6"),
    DELIVERED("Delivered", "#2ecc71"),
    CANCELLED("Cancelled", "#e74c3c");

    private final String label;
    private final String badgeColor;

    OrderStatus(String label, String badgeColor) {
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
