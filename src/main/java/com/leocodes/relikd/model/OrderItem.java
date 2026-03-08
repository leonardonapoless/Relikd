package com.leocodes.relikd.model;

public class OrderItem {
    private final int id;
    private final int orderId;
    private final int computerId;
    private final int quantity;
    private final double priceAtPurchase;

    public OrderItem(int id, int orderId, int computerId, int quantity, double priceAtPurchase) {
        this.id = id;
        this.orderId = orderId;
        this.computerId = computerId;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getComputerId() {
        return computerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }
}
