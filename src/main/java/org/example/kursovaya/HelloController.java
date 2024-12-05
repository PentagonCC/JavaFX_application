package org.example.kursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloController {

    static Stage stage;

    @FXML
    public void goAuthHead() throws IOException, SQLException {
        AuthController authController = new AuthController();
        authController.openPage();
        HelloApplication.stage.close();
    }

    @FXML
        public void goAuthMilya(ActionEvent actionEvent) throws IOException, SQLException {
        AuthController authController = new AuthController();
        authController.openPage();
        HelloApplication.stage.close();
    }

    @FXML
        public void goAuthDekabristov(ActionEvent actionEvent) throws IOException, SQLException {
        AuthController authController = new AuthController();
        authController.openPage();
        HelloApplication.stage.close();
    }
}