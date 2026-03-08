package com.leocodes.relikd.model;

import java.time.LocalDateTime;

public class User {
    private final int id;
    private final String username;
    private final String email;
    private final String passwordHash;
    private final String fullName;
    private final Role role;
    private final LocalDateTime createdAt;

    public User(int id, String username, String email, String passwordHash, String fullName, Role role,
            LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.role = role;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
