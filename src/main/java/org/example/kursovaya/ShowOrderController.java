package org.example.kursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowOrderController {

    static Stage stage = new Stage();

    public void openPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Show-order.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Заказы");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public void goBack(ActionEvent actionEvent) {
        MainWindowController.stage.show();
        ShowOrderController.stage.close();
    }
}
