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

    private static int currentOffice;

    public static int getCurrentOffice() {
        return currentOffice;
    }


    public void goMainContent() throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            DBConnectionHead dbConnection = new DBConnectionHead();
            DBConnectionMilya dbConnectionMilya = new DBConnectionMilya();
            DBConnectionDekabristov dbConnectionDekabristov = new DBConnectionDekabristov();
            dbConnection.headOfficeConnection();
            dbConnectionMilya.milyaDBConnection();
            dbConnectionDekabristov.dekabristovDBConnection();
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
                    currentOffice = 3;
                    MainWindowController mainPage = new MainWindowController();
                    mainPage.openPage();
                    AuthController.stage.close();
                } else if (dbConnectionMilya.checkEmployee(email, password)) {
                    currentOffice = 1;
                    MainWindowController mainPage = new MainWindowController();
                    mainPage.openPage();
                    AuthController.stage.close();
                } else if (dbConnectionDekabristov.checkEmployee(email,password)) {
                    currentOffice = 2;
                    MainWindowController mainPage = new MainWindowController();
                    mainPage.openPage();
                    AuthController.stage.close();
                }
                else {
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
