package com.leocodes.relikd.model;

import java.time.LocalDateTime;

public class Computer {
    private final int id;
    private final String brand;
    private final String model;
    private final int year;
    private final Era era;
    private final LocalDateTime createdAt;

    private Condition condition;
    private double price;
    private int stock;
    private String description;
    private String specsAsJson;
    private String imagePath;

    public Computer(int id, String brand, String model, int year, Era era, Condition condition,
            double price, int stock, String description, String specsAsJson, String imagePath,
            LocalDateTime createdAt) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.era = era;
        this.condition = condition;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.specsAsJson = specsAsJson;
        this.imagePath = imagePath;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public Era getEra() {
        return era;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecsAsJson() {
        return specsAsJson;
    }

    public void setSpecsFromJson(String specsAsJson) {
        this.specsAsJson = specsAsJson;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isInStock() {
        return this.stock > 0;
    }
}
