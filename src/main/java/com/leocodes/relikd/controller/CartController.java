package com.leocodes.relikd.controller;

import com.leocodes.relikd.dao.OrderDAO;
import com.leocodes.relikd.model.Order;
import com.leocodes.relikd.model.OrderStatus;
import com.leocodes.relikd.service.CartService;
import com.leocodes.relikd.util.Navigator;
import com.leocodes.relikd.util.Session;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class CartController implements Initializable {

    @FXML
    private VBox cartItemsBox;
    @FXML
    private Label totalLabel;
    @FXML
    private Button checkoutButton;
    @FXML
    private ProgressIndicator loadingIndicator;

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        renderCart();
    }

    private void renderCart() {
        cartItemsBox.getChildren().clear();
        var items = CartService.get().getItems();
        var computers = CartService.get().getComputerReferences();

        if (items.isEmpty()) {
            Label emptyLabel = new Label("Your cart is empty. Go find something good.");
            emptyLabel.getStyleClass().add("empty-state-label");
            cartItemsBox.getChildren().add(emptyLabel);
            checkoutButton.setDisable(true);
            totalLabel.setText("$0.00");
            return;
        }

        checkoutButton.setDisable(false);

        NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);

        for (var item : items) {
            var cOpt = computers.stream().filter(c -> c.getId() == item.getComputerId()).findFirst();
            if (cOpt.isEmpty())
                continue;
            var computer = cOpt.get();

            HBox row = new HBox(15);
            row.setAlignment(Pos.CENTER_LEFT);
            row.getStyleClass().add("cart-row");

            VBox info = new VBox(5);
            Label nameLabel = new Label(
                    computer.getBrand() + " " + computer.getModel() + " (" + computer.getYear() + ")");
            nameLabel.getStyleClass().add("card-title");

            Label qtyLabel = new Label("Qty: " + item.getQuantity() + " @ " + fmt.format(item.getPriceAtPurchase()));
            qtyLabel.getStyleClass().add("card-year");
            info.getChildren().addAll(nameLabel, qtyLabel);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Label totalItemLabel = new Label(fmt.format(item.getQuantity() * item.getPriceAtPurchase()));
            totalItemLabel.getStyleClass().add("card-price");

            Button removeButton = new Button("Remove");
            removeButton.getStyleClass().add("btn-danger");
            removeButton.setOnAction(e -> {
                CartService.get().removeItem(computer.getId());
                renderCart();
            });

            row.getChildren().addAll(info, spacer, totalItemLabel, removeButton);
            cartItemsBox.getChildren().add(row);
        }

        totalLabel.setText(fmt.format(CartService.get().getTotal()));
    }

    @FXML
    public void onCheckout() {
        if (!Session.get().isLoggedIn()) {
            showError("You must be logged in to checkout.");
            Navigator.navigateTo("Login.fxml");
            return;
        }

        checkoutButton.setDisable(true);
        checkoutButton.setText("Processing...");
        loadingIndicator.setVisible(true);

        Task<Void> checkoutTask = new Task<>() {
            @Override
            protected Void call() throws SQLException {
                int userId = Session.get().getUser().getId();
                double total = CartService.get().getTotal();
                Order order = new Order(0, userId, OrderStatus.PENDING, total, "Web order", LocalDateTime.now());

                orderDAO.placeOrder(order, CartService.get().getItems());
                return null;
            }
        };

        checkoutTask.setOnSucceeded(e -> {
            loadingIndicator.setVisible(false);
            checkoutButton.setText("Place Order");
            CartService.get().clear();
            showSuccess("Your order has been placed successfully!");
            Navigator.navigateTo("OrderHistory.fxml");
        });

        checkoutTask.setOnFailed(e -> {
            loadingIndicator.setVisible(false);
            checkoutButton.setDisable(false);
            checkoutButton.setText("Place Order");
            showError("Checkout failed: " + e.getSource().getException().getMessage());
        });

        new Thread(checkoutTask).start();
    }

    @FXML
    public void onBack() {
        Navigator.navigateTo("Catalog.fxml");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Checkout Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Checkout Complete");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
