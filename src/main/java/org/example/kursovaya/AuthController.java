package org.example.kursovaya;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthController {
    static Stage stage = new Stage();

    public void goMainContent() throws IOException {
        MainWindowController mainPage = new MainWindowController();
        mainPage.openPage();
        AuthController.stage.close();
    }

    public void openPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Auth.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
    }
}
