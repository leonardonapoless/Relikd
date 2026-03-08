package com.leocodes.relikd.dao;

import com.leocodes.relikd.db.DatabaseManager;
import com.leocodes.relikd.model.OrderItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {

    public List<OrderItem> findByOrderId(int orderId) throws SQLException {
        var sql = "SELECT * FROM order_items WHERE order_id = ?";
        var items = new ArrayList<OrderItem>();
        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapRow(rs));
                }
            }
        }
        return items;
    }

    void insert(Connection conn, int assignedOrderId, OrderItem item) throws SQLException {
        var sql = "INSERT INTO order_items (order_id, computer_id, quantity, price_at_purchase) VALUES (?, ?, ?, ?)";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, assignedOrderId);
            stmt.setInt(2, item.getComputerId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getPriceAtPurchase());
            stmt.executeUpdate();
        }
    }

    private OrderItem mapRow(ResultSet rs) throws SQLException {
        return new OrderItem(
                rs.getInt("id"),
                rs.getInt("order_id"),
                rs.getInt("computer_id"),
                rs.getInt("quantity"),
                rs.getDouble("price_at_purchase"));
    }
}
