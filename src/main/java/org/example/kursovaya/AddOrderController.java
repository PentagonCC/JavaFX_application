package org.example.kursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddOrderController {
    static Stage stage = new Stage();

    public void openPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Add-order.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);
        stage.setTitle("Добавление заказа");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public void goBack(ActionEvent actionEvent) {
        MainWindowController.stage.show();
        AddOrderController.stage.close();
    }
}
