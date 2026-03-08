package com.leocodes.relikd.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:sqlite:relikd.db");
                
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("""
                        PRAGMA foreign_keys = ON;
                    """);
                }
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void initialize() {
        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate(SchemaSQL.CREATE_USERS);
            stmt.executeUpdate(SchemaSQL.CREATE_COMPUTERS);
            stmt.executeUpdate(SchemaSQL.CREATE_ORDERS);
            stmt.executeUpdate(SchemaSQL.CREATE_ORDER_ITEMS);
            stmt.executeUpdate(SchemaSQL.CREATE_REVIEWS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
