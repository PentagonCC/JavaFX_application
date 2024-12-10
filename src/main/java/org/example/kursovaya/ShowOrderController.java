package org.example.kursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowOrderController {

    static Stage stage = new Stage();

    @FXML
    public TableView<Order> tableOrder;
    @FXML
    public TableColumn<Order, String> product;
    @FXML
    public TableColumn<Order, String> client;
    @FXML
    public TableColumn<Order, String> employee;

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
