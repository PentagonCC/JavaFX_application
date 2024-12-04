package org.example.kursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddClientController {

    static Stage stage = new Stage();

    @FXML
    private TextField nameClient;

    @FXML
    private TextField surnameClient;

    @FXML
    private TextField patronymicClient;

    @FXML
    private TextField numberClient;



    public void openPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Add-client.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Добавление клиента");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public void addClient(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(!nameClient.getText().isEmpty() && !patronymicClient.getText().isEmpty() && !surnameClient.getText().isEmpty() && !numberClient.getText().isEmpty()){
            alert.setTitle("Успех");
            alert.setHeaderText(null);
            alert.setContentText("Клиент добавлен!");
        }
        else {
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Проверьте заполненность полей");
        }
        alert.showAndWait();
    }

    public void goBack(ActionEvent actionEvent) {
        MainWindowController.stage.show();
        AddClientController.stage.close();

    }
}
