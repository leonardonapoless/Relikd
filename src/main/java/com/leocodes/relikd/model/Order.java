package com.leocodes.relikd.model;

import java.time.LocalDateTime;

public record Order(
        int id,
        int userId,
        OrderStatus status,
        double total,
        String notes,
        LocalDateTime createdAt) {
}
