package com.devstack.lms;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        URL resource = getClass().getResource("./view/LoginForm.fxml");
//
//        Parent parent = FXMLLoader.load(resource);
//        Scene scene = new Scene(parent);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Login Form");
//        primaryStage.show();


        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("./view/LoginForm.fxml"))));
        primaryStage.setTitle("Login");
        primaryStage.show();

    }
}
