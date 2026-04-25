package com.leocodes.relikd.model;

import java.time.LocalDateTime;

public record Computer(
        int id,
        String brand,
        String model,
        int year,
        Era era,
        Condition condition,
        double price,
        int stock,
        String description,
        String specsAsJson,
        String imagePath,
        LocalDateTime createdAt) {

    public boolean isInStock() {
        return stock > 0;
    }
}
