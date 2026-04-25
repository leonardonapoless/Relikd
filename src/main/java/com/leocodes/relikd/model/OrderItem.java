package com.leocodes.relikd.model;

public record OrderItem(
        int id,
        int orderId,
        int computerId,
        int quantity,
        double priceAtPurchase) {
}
