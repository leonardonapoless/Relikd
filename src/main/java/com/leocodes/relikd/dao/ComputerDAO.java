package com.leocodes.relikd.dao;

import com.leocodes.relikd.db.DatabaseManager;
import com.leocodes.relikd.model.Computer;
import com.leocodes.relikd.model.Condition;
import com.leocodes.relikd.model.Era;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComputerDAO {

    public Optional<Computer> findById(int id) throws SQLException {
        var sql = "SELECT * FROM computers WHERE id = ?";
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

    public List<Computer> findAll() throws SQLException {
        var sql = "SELECT * FROM computers ORDER BY created_at DESC";
        var computers = new ArrayList<Computer>();

        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql);
                var rs = stmt.executeQuery()) {
            while (rs.next()) {
                computers.add(mapRow(rs));
            }
        }
        return computers;
    }

    public List<Computer> findByEra(Era era) throws SQLException {
        var sql = "SELECT * FROM computers WHERE era = ? ORDER BY created_at DESC";
        var computers = new ArrayList<Computer>();

        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, era.name().toLowerCase());
            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    computers.add(mapRow(rs));
                }
            }
        }
        return computers;
    }

    public void save(Computer computer) throws SQLException {
        var sql = """
                INSERT INTO computers (brand, model, year, era, condition, price, stock,
                                       description, specs, image_path, created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, computer.getBrand());
            stmt.setString(2, computer.getModel());
            stmt.setInt(3, computer.getYear());
            stmt.setString(4, computer.getEra().name().toLowerCase());
            stmt.setString(5, computer.getCondition().name().toLowerCase());
            stmt.setDouble(6, computer.getPrice());
            stmt.setInt(7, computer.getStock());
            stmt.setString(8, computer.getDescription());
            stmt.setString(9, computer.getSpecsAsJson());
            stmt.setString(10, computer.getImagePath());
            stmt.setString(11, computer.getCreatedAt().toString());
            stmt.executeUpdate();
        }
    }

    public void updateStock(int computerId, int newStock) throws SQLException {
        var sql = "UPDATE computers SET stock = ? WHERE id = ?";
        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, newStock);
            stmt.setInt(2, computerId);
            stmt.executeUpdate();
        }
    }

    public boolean update(Computer computer) throws SQLException {
        var sql = """
                UPDATE computers
                SET brand = ?, model = ?, year = ?, era = ?, condition = ?, price = ?, stock = ?,
                    description = ?, specs = ?, image_path = ?
                WHERE id = ?
                """;
        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, computer.getBrand());
            stmt.setString(2, computer.getModel());
            stmt.setInt(3, computer.getYear());
            stmt.setString(4, computer.getEra().name().toLowerCase());
            stmt.setString(5, computer.getCondition().name().toLowerCase());
            stmt.setDouble(6, computer.getPrice());
            stmt.setInt(7, computer.getStock());
            stmt.setString(8, computer.getDescription());
            stmt.setString(9, computer.getSpecsAsJson());
            stmt.setString(10, computer.getImagePath());
            stmt.setInt(11, computer.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public void delete(int id) throws SQLException {
        var sql = "DELETE FROM computers WHERE id = ?";
        try (var stmt = DatabaseManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    private Computer mapRow(ResultSet rs) throws SQLException {
        return new Computer(
                rs.getInt("id"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getInt("year"),
                Era.valueOf(rs.getString("era").toUpperCase()),
                Condition.valueOf(rs.getString("condition").toUpperCase()),
                rs.getDouble("price"),
                rs.getInt("stock"),
                rs.getString("description"),
                rs.getString("specs"),
                rs.getString("image_path"),
                LocalDateTime.parse(rs.getString("created_at")));
    }
}
