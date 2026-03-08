package com.leocodes.relikd.controller;

import com.leocodes.relikd.util.Navigator;
import com.leocodes.relikd.util.ThemeManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private Button themeToggleButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateButtonText();
    }

    @FXML
    private void onToggleTheme() {
        ThemeManager.get().toggleTheme();
        updateButtonText();
    }

    private void updateButtonText() {
        boolean dark = ThemeManager.get().isDarkMode();
        themeToggleButton.setText(dark ? "\u2600  Switch to Light" : "\u263E  Switch to Dark");
    }

    @FXML
    public void onBack() {
        Navigator.navigateTo("Catalog.fxml");
    }
}
