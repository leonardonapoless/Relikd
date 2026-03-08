package com.leocodes.relikd.db;

public class SchemaSQL {
    public static final String CREATE_USERS = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                email TEXT NOT NULL UNIQUE,
                password_hash TEXT NOT NULL,
                full_name TEXT,
                role TEXT NOT NULL DEFAULT 'customer',
                created_at TEXT NOT NULL
            );
            """;

    public static final String CREATE_COMPUTERS = """
            CREATE TABLE IF NOT EXISTS computers (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                brand TEXT NOT NULL,
                model TEXT NOT NULL,
                year INTEGER NOT NULL,
                era TEXT NOT NULL,
                condition TEXT NOT NULL,
                price REAL NOT NULL,
                stock INTEGER NOT NULL DEFAULT 1,
                description TEXT,
                specs TEXT,
                image_path TEXT,
                created_at TEXT NOT NULL
            );
            """;

    public static final String CREATE_ORDERS = """
            CREATE TABLE IF NOT EXISTS orders (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL REFERENCES users(id),
                status TEXT NOT NULL DEFAULT 'pending',
                total REAL NOT NULL,
                notes TEXT,
                created_at TEXT NOT NULL
            );
            """;

    public static final String CREATE_ORDER_ITEMS = """
            CREATE TABLE IF NOT EXISTS order_items (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                order_id INTEGER NOT NULL REFERENCES orders(id),
                computer_id INTEGER NOT NULL REFERENCES computers(id),
                quantity INTEGER NOT NULL DEFAULT 1,
                price_at_purchase REAL NOT NULL
            );
            """;

    public static final String CREATE_REVIEWS = """
            CREATE TABLE IF NOT EXISTS reviews (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL REFERENCES users(id),
                computer_id INTEGER NOT NULL REFERENCES computers(id),
                rating INTEGER NOT NULL,
                comment TEXT,
                created_at TEXT NOT NULL
            );
            """;
}
