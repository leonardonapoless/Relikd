package com.leocodes.relikd.controller;

import com.leocodes.relikd.dao.ComputerDAO;
import com.leocodes.relikd.dao.ReviewDAO;
import com.leocodes.relikd.model.Computer;
import com.leocodes.relikd.model.Review;
import com.leocodes.relikd.service.CartService;
import com.leocodes.relikd.util.ImageCache;
import com.leocodes.relikd.util.Navigator;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ComputerDetailController implements Initializable {

    private static int selectedComputerId = -1;

    @FXML private Label titleLabel;
    @FXML private Label priceLabel;
    @FXML private Label conditionBadge;
    @FXML private Label eraBadge;
    @FXML private Label descriptionLabel;
    @FXML private Label stockLabel;
    @FXML private Label breadcrumbLabel;
    @FXML private Label averageRatingLabel;
    @FXML private Button addToCartButton;
    @FXML private ProgressIndicator loadingIndicator;
    @FXML private VBox reviewsBox;
    @FXML private GridPane specsGrid;
    @FXML private HBox relatedBox;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private ImageView productImage;
    @FXML private Label noImageLabel;

    private final ComputerDAO computerDAO = new ComputerDAO();
    private final ReviewDAO reviewDAO = new ReviewDAO();
    private Computer computer;

    public static void setSelectedComputerId(int id) {
        selectedComputerId = id;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
        loadComputerDetails();
    }

    private void loadComputerDetails() {
        loadingIndicator.setVisible(true);

        Task<Computer> task = new Task<>() {
            @Override
            protected Computer call() throws SQLException {
                return computerDAO.findById(selectedComputerId).orElse(null);
            }
        };

        task.setOnSucceeded(e -> {
            loadingIndicator.setVisible(false);
            this.computer = task.getValue();
            if (this.computer != null) {
                renderDetails();
                loadReviews();
                loadRelatedMachines();
            } else {
                showError("Computer not found.");
                Navigator.navigateTo("Catalog.fxml");
            }
        });

        task.setOnFailed(e -> {
            loadingIndicator.setVisible(false);
            showError("Failed to load details.");
        });

        new Thread(task).start();
    }

    private void renderDetails() {
        NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);

        titleLabel.setText(computer.getBrand() + " " + computer.getModel() + " (" + computer.getYear() + ")");
        breadcrumbLabel.setText(computer.getEra().getLabel() + "  ›  " + computer.getBrand());
        priceLabel.setText(fmt.format(computer.getPrice()));

        conditionBadge.setText(computer.getCondition().getLabel());
        conditionBadge.getStyleClass().addAll("badge", "badge-" + computer.getCondition().name().toLowerCase());

        eraBadge.setText(computer.getEra().getLabel());
        eraBadge.getStyleClass().add("badge-era");

        descriptionLabel.setText(
                computer.getDescription() != null ? computer.getDescription() : "No description provided.");

        loadProductImage();
        renderSpecs();
        renderStock();
    }

    private void renderStock() {
        if (computer.isInStock()) {
            stockLabel.setText("\u2713  In Stock — " + computer.getStock() + " available");
            stockLabel.getStyleClass().add("stock-available");
            addToCartButton.setDisable(false);
            quantitySpinner.setDisable(false);

            int max = Math.min(computer.getStock(), 10);
            quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, max, 1));
        } else {
            stockLabel.setText("\u2717  Out of Stock");
            stockLabel.getStyleClass().add("stock-empty");
            addToCartButton.setDisable(true);
            quantitySpinner.setDisable(true);
        }
    }

    private void loadProductImage() {
        String url = computer.getImagePath();
        if (url == null || url.isBlank()) {
            productImage.setVisible(false);
            noImageLabel.setVisible(true);
            return;
        }

        noImageLabel.setText("Loading…");

        ImageCache.loadAsync(url, productImage, () -> {
            if (productImage.getImage() != null && !productImage.getImage().isError()) {
                productImage.setVisible(true);
                noImageLabel.setVisible(false);
            } else {
                productImage.setVisible(false);
                noImageLabel.setVisible(true);
                noImageLabel.setText("No Image Available");
            }
        });
    }

    private void renderSpecs() {
        specsGrid.getChildren().clear();
        specsGrid.getColumnConstraints().clear();

        var col1 = new javafx.scene.layout.ColumnConstraints();
        col1.setPercentWidth(35);
        var col2 = new javafx.scene.layout.ColumnConstraints();
        col2.setPercentWidth(65);
        specsGrid.getColumnConstraints().addAll(col1, col2);

        String specs = computer.getSpecsAsJson();
        if (specs == null || specs.isBlank()) {
            Label noSpecs = new Label("No specifications provided.");
            noSpecs.getStyleClass().add("empty-state-label");
            specsGrid.add(noSpecs, 0, 0, 2, 1);
            return;
        }

        specs = specs.replaceAll("[{}]", "").trim();
        if (specs.startsWith("\"")) specs = specs.substring(0);
        String[] pairs = specs.split(",");

        int row = 0;
        for (String pair : pairs) {
            String[] kv = pair.split(":", 2);
            if (kv.length < 2) continue;

            String key = kv[0].trim().replaceAll("\"", "");
            String value = kv[1].trim().replaceAll("\"", "");

            Label keyLabel = new Label(key);
            keyLabel.getStyleClass().add("spec-key");

            Label valueLabel = new Label(value);
            valueLabel.getStyleClass().add("spec-value");
            valueLabel.setWrapText(true);

            specsGrid.add(keyLabel, 0, row);
            specsGrid.add(valueLabel, 1, row);
            row++;
        }
    }

    private void loadReviews() {
        Task<List<Review>> task = new Task<>() {
            @Override
            protected List<Review> call() throws SQLException {
                return reviewDAO.findByComputerId(computer.getId());
            }
        };

        task.setOnSucceeded(e -> {
            reviewsBox.getChildren().clear();
            var reviews = task.getValue();

            if (reviews.isEmpty()) {
                averageRatingLabel.setText("No Reviews");
                averageRatingLabel.getStyleClass().add("badge-era");
                Label noReviews = new Label("No reviews yet. Be the first to share your thoughts.");
                noReviews.getStyleClass().add("empty-state-label");
                reviewsBox.getChildren().add(noReviews);
                return;
            }

            double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0);
            averageRatingLabel.setText(renderStars((int) Math.round(avg)) + "  " + String.format("%.1f", avg) + " / 5");
            averageRatingLabel.getStyleClass().add("badge-era");

            for (var r : reviews) {
                VBox reviewCard = new VBox(6);
                reviewCard.getStyleClass().add("review-card");

                Label starsLabel = new Label(renderStars(r.getRating()));
                starsLabel.getStyleClass().add("review-stars");

                Label commentLabel = new Label(r.getComment());
                commentLabel.setWrapText(true);
                commentLabel.getStyleClass().add("review-comment");

                reviewCard.getChildren().addAll(starsLabel, commentLabel);
                reviewsBox.getChildren().add(reviewCard);
            }
        });

        new Thread(task).start();
    }

    private String renderStars(int rating) {
        return "\u2605".repeat(rating) + "\u2606".repeat(5 - rating);
    }

    private void loadRelatedMachines() {
        Task<List<Computer>> task = new Task<>() {
            @Override
            protected List<Computer> call() throws SQLException {
                return computerDAO.findByEra(computer.getEra());
            }
        };

        task.setOnSucceeded(e -> {
            relatedBox.getChildren().clear();
            NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);

            var related = task.getValue().stream()
                    .filter(c -> c.getId() != computer.getId())
                    .limit(4)
                    .toList();

            if (related.isEmpty()) {
                Label none = new Label("No other machines from this era.");
                none.getStyleClass().add("empty-state-label");
                relatedBox.getChildren().add(none);
                return;
            }

            for (Computer c : related) {
                VBox card = new VBox(6);
                card.getStyleClass().add("related-card");

                Label name = new Label(c.getBrand() + " " + c.getModel());
                name.getStyleClass().add("card-title");
                name.setWrapText(true);

                Label year = new Label(String.valueOf(c.getYear()));
                year.getStyleClass().add("card-year");

                Label price = new Label(fmt.format(c.getPrice()));
                price.getStyleClass().add("card-price");

                Button viewBtn = new Button("View");
                viewBtn.getStyleClass().add("btn-secondary");
                viewBtn.setOnAction(ev -> {
                    setSelectedComputerId(c.getId());
                    Navigator.navigateTo("ComputerDetail.fxml");
                });

                card.getChildren().addAll(name, year, price, viewBtn);
                relatedBox.getChildren().add(card);
            }
        });

        new Thread(task).start();
    }

    @FXML
    public void onAddToCart() {
        if (computer != null && computer.isInStock()) {
            int qty = quantitySpinner.getValue();
            CartService.get().addItem(computer, qty);
            showSuccess(qty + "x " + computer.getBrand() + " " + computer.getModel() + " added to cart!");
        }
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
        alert.setTitle("Added to Cart");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
