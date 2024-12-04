package org.example.kursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

        @FXML
        public void goAuthHead() throws IOException {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(org.example.kursovaya.HelloApplication.class.getResource("Auth.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Авторизация");
            stage.setScene(scene);
            stage.show();
            org.example.kursovaya.HelloApplication.stage.close();
        }

        @FXML
        public void goAuthMilya(ActionEvent actionEvent) throws IOException {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(org.example.kursovaya.HelloApplication.class.getResource("Auth.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Авторизация");
            stage.setScene(scene);
            stage.show();
            org.example.kursovaya.HelloApplication.stage.close();
        }

        @FXML
        public void goAuthDekabristov(ActionEvent actionEvent) throws IOException {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(org.example.kursovaya.HelloApplication.class.getResource("Auth.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            stage.setTitle("Авторизация");
            stage.setScene(scene);
            stage.show();
            HelloApplication.stage.close();
        }
}