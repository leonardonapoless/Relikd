package com.leocodes.relikd.controller;

import com.leocodes.relikd.service.AuthService;
import com.leocodes.relikd.util.Navigator;
import com.leocodes.relikd.util.ThemeManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignW;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML private TextField fullNameField;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button registerButton;
    @FXML private Button themeToggleButton;
    @FXML private Label errorLabel;

    private final AuthService authService = new AuthService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateThemeIcon();
    }

    private void updateThemeIcon() {
        boolean isDark = ThemeManager.get().isDarkMode();
        var icon = FontIcon.of(isDark ? MaterialDesignW.WEATHER_SUNNY : MaterialDesignW.WEATHER_NIGHT, 18);
        icon.getStyleClass().add("nav-icon");
        themeToggleButton.setGraphic(icon);
    }

    @FXML
    public void onThemeToggle() {
        ThemeManager.get().toggleTheme();
        updateThemeIcon();
    }

    @FXML
    public void onRegisterButtonClicked() {
        errorLabel.setVisible(false);

        String fullName = fullNameField.getText().trim();
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showInlineError("Please fill out all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showInlineError("Passwords do not match.");
            return;
        }

        if (!email.contains("@")) {
            showInlineError("Please enter a valid email address.");
            return;
        }

        registerButton.setDisable(true);
        registerButton.setText("Registering...");

        Task<Void> registerTask = new Task<>() {
            @Override
            protected Void call() throws SQLException {
                authService.register(username, email, fullName, password);
                return null;
            }
        };

        registerTask.setOnSucceeded(e -> {
            registerButton.setDisable(false);

            Platform.runLater(() -> Navigator.navigateTo("Login.fxml"));
        });

        registerTask.setOnFailed(e -> {
            registerButton.setDisable(false);
            registerButton.setText("Create Account");

            Throwable ex = e.getSource().getException();
            if (ex instanceof IllegalArgumentException) {
                showInlineError(ex.getMessage());
            } else {
                showInlineError("Registration failed. Please try again later.");
                ex.printStackTrace();
            }
        });

        new Thread(registerTask).start();
    }

    @FXML
    public void onBackToLoginClicked() {
        Navigator.navigateTo("Login.fxml");
    }

    private void showInlineError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
