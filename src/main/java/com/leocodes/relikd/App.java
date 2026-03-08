package com.leocodes.relikd;

import javafx.application.Application;
import javafx.stage.Stage;

import com.leocodes.relikd.db.DatabaseManager;
import com.leocodes.relikd.db.DataSeeder;

import com.leocodes.relikd.util.Navigator;

public class App extends Application {

    @Override
    public void init() throws Exception {
        DatabaseManager.initialize();
        new DataSeeder().seedIfEmpty();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Relikd - Vintage Computers");
        Navigator.init(primaryStage);
        Navigator.navigateTo("Login.fxml");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
