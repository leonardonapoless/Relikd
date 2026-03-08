package com.leocodes.relikd.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {

    private static Stage primaryStage;
    private static BorderPane mainLayout;

    public static void init(Stage stage) {
        primaryStage = stage;
        mainLayout = new BorderPane();
        mainLayout.setTop(createMenuBar());

        var scene = new Scene(mainLayout, 1024, 768);
        ThemeManager.get().bind(scene);
        primaryStage.setScene(scene);
    }

    public static void navigateTo(String fxmlPath) {
        try {
            var fxml = Navigator.class.getResource("/fxml/" + fxmlPath);
            if (fxml == null) {
                throw new RuntimeException("Cannot find FXML file: /fxml/" + fxmlPath);
            }
            var root = (Node) FXMLLoader.load(fxml);
            mainLayout.setCenter(root);
        } catch (IOException e) {
            throw new RuntimeException("Navigation failed: " + fxmlPath, e);
        }
    }

    private static MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setUseSystemMenuBar(true);

        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Quit");
        exitItem.setOnAction(e -> javafx.application.Platform.exit());
        fileMenu.getItems().add(exitItem);

        Menu viewMenu = new Menu("View");
        MenuItem toggleTheme = new MenuItem("Toggle Dark/Light Mode");
        toggleTheme.setOnAction(e -> ThemeManager.get().toggleTheme());
        viewMenu.getItems().add(toggleTheme);

        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About Relikd");
        aboutItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About Relikd");
            alert.setHeaderText("Relikd - Vintage Computer Store");
            alert.setContentText("Version 1.0\nCreated with JavaFX");
            alert.showAndWait();
        });
        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, viewMenu, helpMenu);
        return menuBar;
    }
}
