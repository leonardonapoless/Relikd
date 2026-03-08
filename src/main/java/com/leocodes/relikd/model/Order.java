package com.leocodes.relikd.model;

import java.time.LocalDateTime;

public class Order {
    private final int id;
    private final int userId;
    private final LocalDateTime createdAt;

    private OrderStatus status;
    private double total;
    private String notes;

    public Order(int id, int userId, OrderStatus status, double total, String notes, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.total = total;
        this.notes = notes;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
