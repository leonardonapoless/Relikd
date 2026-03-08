package com.leocodes.relikd.controller;

import com.leocodes.relikd.dao.ComputerDAO;
import com.leocodes.relikd.dao.OrderDAO;
import com.leocodes.relikd.model.Computer;
import com.leocodes.relikd.model.Condition;
import com.leocodes.relikd.model.Era;
import com.leocodes.relikd.model.Order;
import com.leocodes.relikd.model.OrderStatus;
import com.leocodes.relikd.util.Navigator;
import com.leocodes.relikd.util.Session;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private ListView<Computer> inventoryListView;
    @FXML
    private TextField searchField;
    @FXML
    private TextField brandField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField yearField;
    @FXML
    private ComboBox<Era> eraCombo;
    @FXML
    private ComboBox<Condition> conditionCombo;
    @FXML
    private TextField priceField;
    @FXML
    private TextField stockField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextArea specsArea;
    @FXML
    private Button saveComputerButton;

    @FXML
    private ListView<Order> ordersListView;
    @FXML
    private ComboBox<OrderStatus> statusCombo;
    @FXML
    private Button updateStatusButton;

    private final ComputerDAO computerDAO = new ComputerDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    private Computer selectedComputer = null;
    private Order selectedOrder = null;
    private List<Computer> allComputers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!Session.get().isAdmin()) {
            Navigator.navigateTo("Catalog.fxml");
            return;
        }

        setupInventoryTab();
        setupOrdersTab();

        loadInventory();
        loadOrders();
    }

    private void setupInventoryTab() {
        eraCombo.setItems(FXCollections.observableArrayList(Era.values()));
        conditionCombo.setItems(FXCollections.observableArrayList(Condition.values()));

        inventoryListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Computer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("[%d] %s %s - Stock: %d", item.getId(), item.getBrand(), item.getModel(),
                            item.getStock()));
                }
            }
        });

        inventoryListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedComputer = newVal;
                populateComputerForm(newVal);
                saveComputerButton.setText("Update Computer");
            }
        });

        searchField.textProperty().addListener((obs, oldText, newText) -> {
            filterInventory(newText);
        });
    }

    private void filterInventory(String query) {
        if (allComputers == null) return;
        
        if (query == null || query.trim().isEmpty()) {
            inventoryListView.setItems(FXCollections.observableArrayList(allComputers));
            return;
        }

        String lowerQuery = query.toLowerCase();
        List<Computer> filtered = allComputers.stream()
                .filter(c -> c.getBrand().toLowerCase().contains(lowerQuery) || 
                             c.getModel().toLowerCase().contains(lowerQuery))
                .toList();
        
        inventoryListView.setItems(FXCollections.observableArrayList(filtered));
    }

    private void setupOrdersTab() {
        statusCombo.setItems(FXCollections.observableArrayList(OrderStatus.values()));

        ordersListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Order item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("Order #%d - User %d - Status: %s - Total: $%.2f",
                            item.getId(), item.getUserId(), item.getStatus().getLabel(), item.getTotal()));
                }
            }
        });

        ordersListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedOrder = newVal;
                statusCombo.setValue(newVal.getStatus());
                updateStatusButton.setDisable(false);
            }
        });
    }

    private void loadInventory() {
        Task<List<Computer>> task = new Task<>() {
            @Override
            protected List<Computer> call() throws SQLException {

                return computerDAO.findAll();
            }
        };

        task.setOnSucceeded(e -> {
            allComputers = task.getValue();
            filterInventory(searchField.getText());
        });
        new Thread(task).start();
    }

    private void loadOrders() {
        Task<List<Order>> task = new Task<>() {
            @Override
            protected List<Order> call() throws SQLException {
                return orderDAO.findAll();
            }
        };

        task.setOnSucceeded(e -> ordersListView.setItems(FXCollections.observableArrayList(task.getValue())));
        new Thread(task).start();
    }

    private void populateComputerForm(Computer c) {
        brandField.setText(c.getBrand());
        modelField.setText(c.getModel());
        yearField.setText(String.valueOf(c.getYear()));
        eraCombo.setValue(c.getEra());
        conditionCombo.setValue(c.getCondition());
        priceField.setText(String.valueOf(c.getPrice()));
        stockField.setText(String.valueOf(c.getStock()));
        descriptionArea.setText(c.getDescription());
        specsArea.setText(c.getSpecsAsJson());
    }

    @FXML
    public void onClearForm() {
        inventoryListView.getSelectionModel().clearSelection();
        selectedComputer = null;
        brandField.clear();
        modelField.clear();
        yearField.clear();
        eraCombo.setValue(null);
        conditionCombo.setValue(null);
        priceField.clear();
        stockField.clear();
        descriptionArea.clear();
        specsArea.clear();
        saveComputerButton.setText("Add New Computer");
    }

    @FXML
    public void onSaveComputer() {
        try {
            String brand = brandField.getText();
            String model = modelField.getText();
            int year = Integer.parseInt(yearField.getText());
            Era era = eraCombo.getValue();
            Condition condition = conditionCombo.getValue();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            String desc = descriptionArea.getText();
            String specs = specsArea.getText();
            if (specs == null || specs.trim().isEmpty()) {
                specs = "{}";
            }

            if (brand.isEmpty() || model.isEmpty() || era == null || condition == null) {
                showError("Please fill out all required fields.");
                return;
            }

            final String finalSpecs = specs;

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws SQLException {
                    if (selectedComputer == null) {
                        Computer newC = new Computer(0, brand, model, year, era, condition, price, stock, desc, finalSpecs,
                                null, LocalDateTime.now());
                        computerDAO.save(newC);
                    } else {
                        selectedComputer.setCondition(condition);
                        selectedComputer.setPrice(price);
                        selectedComputer.setStock(stock);
                        selectedComputer.setDescription(desc);
                        selectedComputer.setSpecsFromJson(finalSpecs);
                        computerDAO.update(selectedComputer);
                    }
                    return null;
                }
            };

            task.setOnSucceeded(e -> {
                showSuccess("Inventory updated successfully.");
                onClearForm();
                loadInventory();
            });

            task.setOnFailed(e -> {
                showError("Failed to save to database.");
                e.getSource().getException().printStackTrace();
            });

            new Thread(task).start();

        } catch (NumberFormatException e) {
            showError("Please enter valid numbers for Year, Price, and Stock.");
        }
    }

    @FXML
    public void onUpdateOrderStatus() {
        if (selectedOrder == null || statusCombo.getValue() == null)
            return;

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws SQLException {
                orderDAO.updateStatus(selectedOrder.getId(), statusCombo.getValue());
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            showSuccess("Order status updated.");
            loadOrders();
            updateStatusButton.setDisable(true);
        });

        task.setOnFailed(e -> showError("Failed to update status."));
        new Thread(task).start();
    }

    @FXML
    public void onBack() {
        Navigator.navigateTo("Catalog.fxml");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
