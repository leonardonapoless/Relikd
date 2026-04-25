package com.leocodes.relikd.dao;

import com.leocodes.relikd.db.DatabaseManager;
import com.leocodes.relikd.model.Role;
import com.leocodes.relikd.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserDAO {

    public Optional<User> findById(int id) throws SQLException {
        var sql = "SELECT * FROM users WHERE id = ?";
        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<User> findByUsername(String username) throws SQLException {
        var sql = "SELECT * FROM users WHERE username = ?";
        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) throws SQLException {
        var sql = "SELECT * FROM users WHERE email = ?";
        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, email);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    public void save(User user) throws SQLException {
        var sql = """
                INSERT INTO users (username, email, password_hash, full_name, role, created_at)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.username());
            stmt.setString(2, user.email());
            stmt.setString(3, user.passwordHash());
            stmt.setString(4, user.fullName());
            stmt.setString(5, user.role().name().toLowerCase());
            stmt.setString(6, user.createdAt().toString());
            stmt.executeUpdate();
        }
    }

    public int countAll() throws SQLException {
        var sql = "SELECT COUNT(*) FROM users";
        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    private User mapRow(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("full_name"),
                Role.valueOf(rs.getString("role").toUpperCase()),
                LocalDateTime.parse(rs.getString("created_at")));
    }
}
