package com.leocodes.relikd.controller;

import com.leocodes.relikd.dao.OrderDAO;
import com.leocodes.relikd.dao.OrderItemDAO;
import com.leocodes.relikd.dao.ComputerDAO;
import com.leocodes.relikd.model.Order;
import com.leocodes.relikd.model.OrderItem;
import com.leocodes.relikd.model.Computer;
import com.leocodes.relikd.util.Navigator;
import com.leocodes.relikd.util.Session;
import com.leocodes.relikd.util.DateUtil;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class OrderHistoryController implements Initializable {

    @FXML
    private VBox ordersBox;
    @FXML
    private ProgressIndicator loadingIndicator;

    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();
    private final ComputerDAO computerDAO = new ComputerDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!Session.get().isLoggedIn()) {
            Navigator.navigateTo("Login.fxml");
            return;
        }
        loadOrders();
    }

    private void loadOrders() {
        loadingIndicator.setVisible(true);

        Task<List<Order>> task = new Task<>() {
            @Override
            protected List<Order> call() throws SQLException {
                return orderDAO.findByUserId(Session.get().getUser().id());
            }
        };

        task.setOnSucceeded(e -> {
            loadingIndicator.setVisible(false);
            renderOrders(task.getValue());
        });

        task.setOnFailed(e -> {
            loadingIndicator.setVisible(false);
            showError("Failed to load order history.");
            e.getSource().getException().printStackTrace();
        });

        new Thread(task).start();
    }

    private void renderOrders(List<Order> orders) {
        ordersBox.getChildren().clear();

        if (orders.isEmpty()) {
            Label empty = new Label("No orders yet. Your first vintage machine awaits.");
            empty.getStyleClass().add("empty-state-label");
            ordersBox.getChildren().add(empty);
            return;
        }

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

        for (Order order : orders) {
            VBox orderCard = new VBox(15);
            orderCard.getStyleClass().add("card");

            HBox header = new HBox(15);
            header.setAlignment(Pos.CENTER_LEFT);

            Label orderIdLabel = new Label("Order #" + order.id());
            orderIdLabel.getStyleClass().add("card-title");

            Label dateLabel = new Label(DateUtil.formatForDisplay(order.createdAt()));
            dateLabel.getStyleClass().add("card-year");

            Label topTotal = new Label(currencyFormat.format(order.total()));
            topTotal.getStyleClass().add("card-price");

            Label statusBadge = new Label(order.status().getLabel());
            statusBadge.getStyleClass().addAll("badge", "status-" + order.status().name().toLowerCase());

            header.getChildren().addAll(orderIdLabel, dateLabel, topTotal, statusBadge);
            orderCard.getChildren().add(header);

            VBox itemsBox = new VBox(5);
            itemsBox.getStyleClass().add("order-items");

            loadOrderItemsAsync(order.id(), itemsBox, currencyFormat);

            orderCard.getChildren().add(itemsBox);
            ordersBox.getChildren().add(orderCard);
        }
    }

    private void loadOrderItemsAsync(int orderId, VBox container, NumberFormat fmt) {
        Task<List<OrderItem>> itemTask = new Task<>() {
            @Override
            protected List<OrderItem> call() throws SQLException {
                return orderItemDAO.findByOrderId(orderId);
            }
        };

        itemTask.setOnSucceeded(e -> {
            var items = itemTask.getValue();
            for (var item : items) {
                fetchComputerDetails(item, container, fmt);
            }
        });

        new Thread(itemTask).start();
    }

    private void fetchComputerDetails(OrderItem item, VBox container, NumberFormat fmt) {
        Task<Computer> fetchTask = new Task<>() {
            @Override
            protected Computer call() throws SQLException {
                return computerDAO.findById(item.computerId()).orElse(null);
            }
        };
        fetchTask.setOnSucceeded(e -> {
            Computer c = fetchTask.getValue();
            if (c != null) {
                Label label = new Label(item.quantity() + "x " + c.brand() + " " + c.model() + " @ "
                        + fmt.format(item.priceAtPurchase()));
                label.getStyleClass().add("order-item-label");
                container.getChildren().add(label);
            }
        });
        new Thread(fetchTask).start();
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
}
