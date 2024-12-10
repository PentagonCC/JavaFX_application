package org.example.kursovaya;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    public TableColumn<Order, String> office;
    @FXML
    public TableColumn<Order, Integer> numOrder;

    public static ObservableList<Order> orderList = FXCollections.observableArrayList();

    public void openPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Show-order.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Заказы");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);
    }

    public void goBack(ActionEvent actionEvent) {
        MainWindowController.stage.show();
        ShowOrderController.stage.close();
    }

    public void showOrder(){
        int officeID = AuthController.getCurrentOffice();
        product.setCellValueFactory(new PropertyValueFactory<>("products"));
        client.setCellValueFactory(new PropertyValueFactory<>("fioClient"));
        employee.setCellValueFactory(new PropertyValueFactory<>("fioEmployee"));
        office.setCellValueFactory(new PropertyValueFactory<>("officeAddress"));
        numOrder.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        if (officeID == 3) {
            DBConnectionHead dbConnectionHead = new DBConnectionHead();
            dbConnectionHead.loadOrderFromDB();
        } else if (officeID == 2) {
            DBConnectionDekabristov dbConnectionDekabristov = new DBConnectionDekabristov();
            dbConnectionDekabristov.loadOrderFromDB();
        } else if (officeID == 1) {
            DBConnectionMilya dbConnectionMilya = new DBConnectionMilya();
            dbConnectionMilya.loadOrderFromDB();
        }
        tableOrder.setItems(orderList);

    }


}
