package com.example.drivingsystem;

import com.example.drivingsystem.fxControllers.LoginPage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stg;
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Driving System");
//        stage.setScene(scene);
//        stage.show();
        stg = stage;
        stage.setResizable(false);
        stage.centerOnScreen();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void changeScene(String fxml, double width, double height) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.setResizable(false);
        stg.setWidth(width);
        stg.setHeight(height);
        stg.centerOnScreen();
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch();
    }
}