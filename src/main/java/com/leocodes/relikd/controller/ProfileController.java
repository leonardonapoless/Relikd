package com.leocodes.relikd.controller;

import com.leocodes.relikd.model.User;
import com.leocodes.relikd.util.DateUtil;
import com.leocodes.relikd.util.Navigator;
import com.leocodes.relikd.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Label fullNameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label memberSinceLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!Session.get().isLoggedIn()) {
            Navigator.navigateTo("Login.fxml");
            return;
        }

        User user = Session.get().getUser();
        fullNameLabel.setText(user.getFullName());
        usernameLabel.setText("@" + user.getUsername());
        emailLabel.setText(user.getEmail());
        roleLabel.setText(user.getRole().name().charAt(0) + user.getRole().name().substring(1).toLowerCase());
        memberSinceLabel.setText(DateUtil.formatForDisplay(user.getCreatedAt()));
    }

    @FXML
    public void onSignOut() {
        Session.get().logout();
        Navigator.navigateTo("Login.fxml");
    }

    @FXML
    public void onBack() {
        Navigator.navigateTo("Catalog.fxml");
    }
}
