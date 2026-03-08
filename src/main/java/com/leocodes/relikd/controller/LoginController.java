package com.leocodes.relikd.controller;

import com.leocodes.relikd.model.User;
import com.leocodes.relikd.service.AuthService;
import com.leocodes.relikd.util.Navigator;
import com.leocodes.relikd.util.Session;
import com.leocodes.relikd.util.ThemeManager;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
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
    public void onLoginButtonClicked() {
        errorLabel.setVisible(false);
        String identifier = usernameField.getText().trim();
        String password = passwordField.getText();

        if (identifier.isEmpty() || password.isEmpty()) {
            showInlineError("Please enter both username/email and password.");
            return;
        }

        loginButton.setDisable(true);
        loginButton.setText("Signing in...");

        Task<Optional<User>> loginTask = new Task<>() {
            @Override
            protected Optional<User> call() throws SQLException {
                return authService.authenticate(identifier, password);
            }
        };

        loginTask.setOnSucceeded(e -> {
            loginButton.setDisable(false);
            loginButton.setText("Sign In");
            loginTask.getValue().ifPresentOrElse(
                    user -> {
                        Session.get().login(user);
                        Navigator.navigateTo("Catalog.fxml");
                    },
                    () -> showInlineError("Wrong username/email or password."));
        });

        loginTask.setOnFailed(e -> {
            loginButton.setDisable(false);
            loginButton.setText("Sign In");
            showInlineError("Database error during login. Try again later.");
            e.getSource().getException().printStackTrace();
        });

        new Thread(loginTask).start();
    }

    @FXML
    public void onRegisterLinkClicked() {
        Navigator.navigateTo("Register.fxml");
    }

    private void showInlineError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
