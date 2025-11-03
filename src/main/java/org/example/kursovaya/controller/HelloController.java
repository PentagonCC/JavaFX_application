package org.example.kursovaya.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.example.kursovaya.HelloApplication;

import java.io.IOException;

public class HelloController {

    static Stage stage;

    @FXML
    public void goAuthHead() throws IOException {
        AuthController authController = new AuthController();
        authController.openPage();
        HelloApplication.stage.close();
    }

    @FXML
        public void goAuthMilya(ActionEvent actionEvent) throws IOException {
        AuthController authController = new AuthController();
        authController.openPage();
        HelloApplication.stage.close();
    }

    @FXML
        public void goAuthDekabristov(ActionEvent actionEvent) throws IOException{
        AuthController authController = new AuthController();
        authController.openPage();
        HelloApplication.stage.close();
    }
}