package com.leocodes.relikd.dao;

import com.leocodes.relikd.db.DatabaseManager;
import com.leocodes.relikd.model.Order;
import com.leocodes.relikd.model.OrderItem;
import com.leocodes.relikd.model.OrderStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAO {

    public Optional<Order> findById(int id) throws SQLException {
        var sql = "SELECT * FROM orders WHERE id = ?";
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

    public List<Order> findByUserId(int userId) throws SQLException {
        var sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
        var orders = new ArrayList<Order>();
        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapRow(rs));
                }
            }
        }
        return orders;
    }

    public List<Order> findAll() throws SQLException {
        var sql = "SELECT * FROM orders ORDER BY created_at DESC";
        var orders = new ArrayList<Order>();
        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql);
                var rs = stmt.executeQuery()) {
            while (rs.next()) {
                orders.add(mapRow(rs));
            }
        }
        return orders;
    }

    public void updateStatus(int orderId, OrderStatus status) throws SQLException {
        var sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, status.name().toLowerCase());
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        }
    }

    public void placeOrder(Order order, List<OrderItem> items) throws SQLException {
        var conn = DatabaseManager.getConnection();
        boolean originalAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);

        try {
            int orderId = insertOrder(conn, order);

            var orderItemDAO = new OrderItemDAO();
            var computerDAO = new ComputerDAO();

            for (var item : items) {
                orderItemDAO.insert(conn, orderId, item);

                var sqlStock = "UPDATE computers SET stock = stock - ? WHERE id = ? AND stock >= ?";
                try (var stockStmt = conn.prepareStatement(sqlStock)) {
                    stockStmt.setInt(1, item.quantity());
                    stockStmt.setInt(2, item.computerId());
                    stockStmt.setInt(3, item.quantity());
                    int updated = stockStmt.executeUpdate();
                    if (updated == 0) {
                        throw new SQLException(
                                "Insufficient stock to fulfill order for computer id " + item.computerId());
                    }
                }
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(originalAutoCommit);
        }
    }

    private int insertOrder(Connection conn, Order order) throws SQLException {
        var sql = "INSERT INTO orders (user_id, status, total, notes, created_at) VALUES (?, ?, ?, ?, ?)";
        try (var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.userId());
            stmt.setString(2, order.status().name().toLowerCase());
            stmt.setDouble(3, order.total());
            stmt.setString(4, order.notes());
            stmt.setString(5, order.createdAt().toString());
            stmt.executeUpdate();

            try (var rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Creating order failed, no ID obtained.");
            }
        }
    }

    private Order mapRow(ResultSet rs) throws SQLException {
        return new Order(
                rs.getInt("id"),
                rs.getInt("user_id"),
                OrderStatus.valueOf(rs.getString("status").toUpperCase()),
                rs.getDouble("total"),
                rs.getString("notes"),
                LocalDateTime.parse(rs.getString("created_at")));
    }
}
