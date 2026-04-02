package com.leocodes.relikd.controller;

import com.leocodes.relikd.dao.ComputerDAO;
import com.leocodes.relikd.model.Condition;
import com.leocodes.relikd.model.Computer;
import com.leocodes.relikd.model.Era;
import com.leocodes.relikd.util.ImageCache;
import com.leocodes.relikd.util.Navigator;
import com.leocodes.relikd.util.Session;
import com.leocodes.relikd.util.ThemeManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.collections.FXCollections;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignS;
import org.kordamp.ikonli.materialdesign2.MaterialDesignW;

import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class CatalogController implements Initializable {

    @FXML
    private Label welcomeLabel;
    @FXML
    private ListView<List<Computer>> catalogListView;
    @FXML
    private ComboBox<String> eraFilter;
    @FXML
    private ComboBox<String> brandFilter;
    @FXML
    private ComboBox<String> conditionFilter;
    @FXML
    private ComboBox<String> sortFilter;
    @FXML
    private TextField searchField;
    @FXML
    private Label resultsLabel;
    @FXML
    private ProgressIndicator loadingIndicator;

    @FXML
    private Button cartButton;
    @FXML
    private Button ordersButton;
    @FXML
    private Button adminButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button themeToggleButton;
    @FXML
    private Button settingsButton;

    private final ComputerDAO computerDAO = new ComputerDAO();
    private List<Computer> allComputers;
    private static final ExecutorService vThreads = Executors.newVirtualThreadPerTaskExecutor();

    private static final int CARDS_PER_ROW = 3;
    private static final NumberFormat PRICE_FORMAT = NumberFormat.getCurrencyInstance(Locale.US);

    private static String savedEra = "All";
    private static String savedBrand = "All";
    private static String savedCondition = "All";
    private static String savedSort = "Name A–Z";
    private static String savedSearch = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var user = Session.get().getUser();
        if (user != null) {
            welcomeLabel.setText("Welcome, " + user.getFullName());
        }

        adminButton.setVisible(Session.get().isAdmin());
        adminButton.setManaged(Session.get().isAdmin());

        eraFilter.getItems().add("All");
        eraFilter.getItems().add("── Apple ──");
        for (Era era : Era.appleEras()) {
            eraFilter.getItems().add(era.getLabel());
        }
        eraFilter.getItems().add("── All Brands ──");
        for (Era era : Era.generalEras()) {
            eraFilter.getItems().add(era.getLabel());
        }
        eraFilter.setValue(savedEra);

        brandFilter.getItems().add("All");
        brandFilter.setValue(savedBrand);

        conditionFilter.getItems().add("All");
        for (Condition cond : Condition.values()) {
            conditionFilter.getItems().add(cond.getLabel());
        }
        conditionFilter.setValue(savedCondition);

        sortFilter.getItems().addAll("Name A–Z", "Name Z–A", "Price ↑", "Price ↓", "Year ↑", "Year ↓");
        sortFilter.setValue(savedSort);

        searchField.setText(savedSearch);

        eraFilter.valueProperty().addListener((obs, o, n) -> {
            savedEra = n;
            applyFilters();
        });
        brandFilter.valueProperty().addListener((obs, o, n) -> {
            savedBrand = n;
            applyFilters();
        });
        conditionFilter.valueProperty().addListener((obs, o, n) -> {
            savedCondition = n;
            applyFilters();
        });
        sortFilter.valueProperty().addListener((obs, o, n) -> {
            savedSort = n;
            applyFilters();
        });
        searchField.textProperty().addListener((obs, o, n) -> {
            savedSearch = n;
            applyFilters();
        });

        configureCatalogListView();
        setupNavIcons();
        loadComputers();
    }

    private void configureCatalogListView() {
        catalogListView.setFocusTraversable(false);
        catalogListView.setCellFactory(lv -> new CatalogRowCell());

        catalogListView.setPlaceholder(new Label(""));
    }

    private void setupNavIcons() {
        cartButton.setGraphic(navIcon(MaterialDesignC.CART_OUTLINE));
        ordersButton.setGraphic(navIcon(MaterialDesignP.PACKAGE_VARIANT));
        adminButton.setGraphic(navIcon(MaterialDesignS.SHIELD_ACCOUNT));
        profileButton.setGraphic(navIcon(MaterialDesignA.ACCOUNT_CIRCLE_OUTLINE));
        settingsButton.setGraphic(navIcon(MaterialDesignC.COG_OUTLINE));
        updateThemeIcon();
    }

    private FontIcon navIcon(org.kordamp.ikonli.Ikon ikon) {
        FontIcon icon = FontIcon.of(ikon, 18);
        icon.getStyleClass().add("nav-icon");
        return icon;
    }

    private void updateThemeIcon() {
        boolean isDark = ThemeManager.get().isDarkMode();
        var ikon = isDark ? MaterialDesignW.WEATHER_SUNNY : MaterialDesignW.WEATHER_NIGHT;
        themeToggleButton.setGraphic(navIcon(ikon));
    }

    private void loadComputers() {
        catalogListView.getItems().clear();
        loadingIndicator.setVisible(true);

        Task<List<Computer>> task = new Task<>() {
            @Override
            protected List<Computer> call() throws SQLException {
                return computerDAO.findAll();
            }
        };

        task.setOnSucceeded(e -> {
            allComputers = task.getValue();
            loadingIndicator.setVisible(false);
            populateBrandFilter();
            applyFilters();
        });

        task.setOnFailed(e -> {
            loadingIndicator.setVisible(false);
            showError("Couldn't load catalog listings. Check your database.");
            e.getSource().getException().printStackTrace();
        });

        vThreads.submit(task);
    }

    private void populateBrandFilter() {
        String current = brandFilter.getValue();
        brandFilter.getItems().clear();
        brandFilter.getItems().add("All");

        allComputers.stream()
                .map(Computer::getBrand)
                .distinct()
                .sorted()
                .forEach(b -> brandFilter.getItems().add(b));

        brandFilter.setValue(current != null && brandFilter.getItems().contains(current) ? current : "All");
    }

    private void applyFilters() {
        if (allComputers == null)
            return;

        String selectedEra = eraFilter.getValue();
        String selectedBrand = brandFilter.getValue();
        String selectedCondition = conditionFilter.getValue();
        String selectedSort = sortFilter.getValue();
        String searchText = searchField.getText().toLowerCase();

        var selectedEraEnum = Era.fromLabel(selectedEra);

        var filtered = allComputers.stream()
                .filter(comp -> selectedEraEnum.map(era -> {
                    if (era.isAppleEra())
                        return comp.getEra() == era;
                    return comp.getEra() == era || comp.getEra().getGeneralEra() == era;
                }).orElse(true))
                .filter(comp -> "All".equals(selectedBrand) || comp.getBrand().equals(selectedBrand))
                .filter(comp -> "All".equals(selectedCondition)
                        || comp.getCondition().getLabel().equals(selectedCondition))
                .filter(comp -> comp.getModel().toLowerCase().contains(searchText)
                        || comp.getBrand().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        Comparator<Computer> sorter = switch (selectedSort) {
            case "Name Z–A" ->
                Comparator.comparing((Computer comp) -> comp.getBrand() + " " + comp.getModel()).reversed();
            case "Price ↑" -> Comparator.comparingDouble(Computer::getPrice);
            case "Price ↓" -> Comparator.comparingDouble(Computer::getPrice).reversed();
            case "Year ↑" -> Comparator.comparingInt(Computer::getYear);
            case "Year ↓" -> Comparator.comparingInt(Computer::getYear).reversed();
            default -> Comparator.comparing(comp -> comp.getBrand() + " " + comp.getModel());
        };

        filtered.sort(sorter);

        resultsLabel.setText(filtered.size() + " of " + allComputers.size() + " machines");

        var rows = partitionIntoRows(filtered, CARDS_PER_ROW);
        catalogListView.setItems(FXCollections.observableArrayList(rows));

        if (filtered.isEmpty()) {
            var placeholder = new Label("No machines match that search. Try broader filters.");
            placeholder.getStyleClass().add("empty-state-label");
            catalogListView.setPlaceholder(placeholder);
        }
    }

    private List<List<Computer>> partitionIntoRows(List<Computer> items, int rowSize) {
        var rows = new ArrayList<List<Computer>>();
        for (int i = 0; i < items.size(); i += rowSize) {
            rows.add(items.subList(i, Math.min(i + rowSize, items.size())));
        }
        return rows;
    }

    private class CatalogRowCell extends ListCell<List<Computer>> {

        private final HBox rowBox = new HBox(14);

        CatalogRowCell() {
            rowBox.setAlignment(Pos.TOP_LEFT);
            rowBox.getStyleClass().add("catalog-row");

            setStyle("-fx-background-color: transparent; -fx-padding: 0 0 14 0;");
        }

        @Override
        protected void updateItem(List<Computer> computers, boolean empty) {
            super.updateItem(computers, empty);

            if (empty || computers == null) {
                setGraphic(null);
                return;
            }

            rowBox.getChildren().clear();

            for (Computer comp : computers) {
                rowBox.getChildren().add(buildComputerCard(comp));
            }

            int remaining = CARDS_PER_ROW - computers.size();
            for (int i = 0; i < remaining; i++) {
                Region spacer = new Region();
                spacer.setPrefWidth(210);
                spacer.setMinWidth(210);
                rowBox.getChildren().add(spacer);
            }

            setGraphic(rowBox);
        }
    }

    private VBox buildComputerCard(Computer comp) {
        VBox card = new VBox(6);
        card.getStyleClass().add("catalog-card");
        card.setAlignment(Pos.TOP_LEFT);
        card.setOnMouseClicked(e -> openDetail(comp.getId()));

        StackPane imageArea = new StackPane();
        imageArea.getStyleClass().add("card-image-area");

        ImageView thumb = new ImageView();
        thumb.setFitWidth(200);
        thumb.setFitHeight(140);
        thumb.setPreserveRatio(true);
        thumb.setSmooth(true);

        Label placeholder = new Label("⌘");
        placeholder.getStyleClass().add("card-image-placeholder");

        imageArea.getChildren().addAll(placeholder, thumb);

        String url = comp.getImagePath();
        if (url != null && !url.isEmpty()) {
            ImageCache.loadAsync(url, thumb, () -> {
                if (thumb.getImage() != null && !thumb.getImage().isError()) {
                    placeholder.setVisible(false);
                }
            });
        }

        Label nameLabel = new Label(comp.getBrand() + " " + comp.getModel());
        nameLabel.getStyleClass().add("card-title");
        nameLabel.setWrapText(true);

        HBox metaRow = new HBox(8);
        metaRow.setAlignment(Pos.CENTER_LEFT);
        Label yearLabel = new Label(String.valueOf(comp.getYear()));
        yearLabel.getStyleClass().add("card-year");

        Label conditionBadge = new Label(comp.getCondition().getLabel());
        conditionBadge.getStyleClass().addAll("badge", "badge-" + comp.getCondition().name().toLowerCase());

        metaRow.getChildren().addAll(yearLabel, conditionBadge);

        Label priceLabel = new Label(PRICE_FORMAT.format(comp.getPrice()));
        priceLabel.getStyleClass().add("card-price");

        card.getChildren().addAll(imageArea, nameLabel, metaRow, priceLabel);
        return card;
    }

    private void openDetail(int computerId) {
        ComputerDetailController.setSelectedComputerId(computerId);
        Navigator.navigateTo("ComputerDetail.fxml");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onClearFilters() {
        searchField.clear();
        eraFilter.setValue("All");
        brandFilter.setValue("All");
        conditionFilter.setValue("All");
        sortFilter.setValue("Name A–Z");
    }

    @FXML
    public void onLogoutClicked() {
        Session.get().logout();
        Navigator.navigateTo("Login.fxml");
    }

    @FXML
    public void onCartClicked() {
        Navigator.navigateTo("Cart.fxml");
    }

    @FXML
    public void onOrdersClicked() {
        Navigator.navigateTo("OrderHistory.fxml");
    }

    @FXML
    public void onAdminClicked() {
        Navigator.navigateTo("Admin.fxml");
    }

    @FXML
    public void onThemeToggle() {
        ThemeManager.get().toggleTheme();
        updateThemeIcon();
    }

    @FXML
    public void onProfileClicked() {
        Navigator.navigateTo("Profile.fxml");
    }

    @FXML
    public void onSettingsClicked() {
        Navigator.navigateTo("Settings.fxml");
    }
}