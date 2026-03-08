package com.leocodes.relikd.model;

import java.time.LocalDateTime;

public class Review {
    private final int id;
    private final int userId;
    private final int computerId;
    private final LocalDateTime createdAt;

    private int rating;
    private String comment;

    public Review(int id, int userId, int computerId, int rating, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.computerId = computerId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getComputerId() {
        return computerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
