package com.leocodes.relikd.model;

import java.time.LocalDateTime;

public record User(
        int id,
        String username,
        String email,
        String passwordHash,
        String fullName,
        Role role,
        LocalDateTime createdAt) {
}
