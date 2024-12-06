package org.example.kursovaya;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;

public class AuthController {

    static Stage stage = new Stage();

    @FXML
    public TextField emailEmployee;
    @FXML
    public TextField passwordEmployee;

    public void goMainContent() throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            DBConnectionHead dbConnection = new DBConnectionHead();
            dbConnection.headOfficeConnection();
            String email = emailEmployee.getText();
            String password = passwordEmployee.getText();
            if(email.isEmpty() || password.isEmpty()){
                alert.setTitle("Ошибка данных");
                alert.setHeaderText(null);
                alert.setContentText("Введите данные");
                alert.showAndWait();
            }
            else {
                if (dbConnection.checkEmployee(email, password)) {
                    MainWindowController mainPage = new MainWindowController();
                    mainPage.openPage();
                    AuthController.stage.close();
                } else {
                    alert.setTitle("Ошибка данных");
                    alert.setHeaderText(null);
                    alert.setContentText("Неверный логин или пароль");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Ошибка подключения к базе данных");
            alert.showAndWait();
        }
    }

    public void openPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Auth.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
    }
}
