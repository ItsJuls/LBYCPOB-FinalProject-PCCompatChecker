package com.pccompatchecker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Label welcomeLabel = new Label("PC Compat Checker");
        StackPane root = new StackPane(welcomeLabel);
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("PC Compat Checker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

