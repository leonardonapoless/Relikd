package com.leocodes.relikd.model;

import java.time.LocalDateTime;

public record Review(
        int id,
        int userId,
        int computerId,
        int rating,
        String comment,
        LocalDateTime createdAt) {
}
