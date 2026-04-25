package com.leocodes.relikd.dao;

import com.leocodes.relikd.db.DatabaseManager;
import com.leocodes.relikd.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public List<Review> findByComputerId(int computerId) throws SQLException {
        var sql = "SELECT * FROM reviews WHERE computer_id = ? ORDER BY created_at DESC";
        var reviews = new ArrayList<Review>();
        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, computerId);
            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(mapRow(rs));
                }
            }
        }
        return reviews;
    }

    public void save(Review review) throws SQLException {
        var sql = """
                INSERT INTO reviews (user_id, computer_id, rating, comment, created_at)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, review.userId());
            stmt.setInt(2, review.computerId());
            stmt.setInt(3, review.rating());
            stmt.setString(4, review.comment());
            stmt.setString(5, review.createdAt().toString());
            stmt.executeUpdate();
        }
    }

    private Review mapRow(ResultSet rs) throws SQLException {
        return new Review(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getInt("computer_id"),
                rs.getInt("rating"),
                rs.getString("comment"),
                LocalDateTime.parse(rs.getString("created_at")));
    }
}
