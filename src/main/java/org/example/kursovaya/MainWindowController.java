package org.example.kursovaya;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainWindowController {

    static Stage stage = new Stage();

    @FXML
    public TableView<Product> productTable;
    @FXML
    public TableColumn<Product, String> titleProduct;
    @FXML
    public TableColumn<Product, String> colorProduct;
    @FXML
    public TableColumn<Product, String> thicknessProduct;
    @FXML
    public TableColumn<Product, String> priceProduct;
    @FXML
    public TableColumn<Product, String> quantityProduct;
    @FXML
    public Separator separator;

    public static ObservableList<Product> productList = FXCollections.observableArrayList();

    public void goAddClientForm(ActionEvent actionEvent) throws IOException {
        AddClientController addClientController = new AddClientController();
        addClientController.openPage();
        MainWindowController.stage.hide();
    }

    public void goAddOrderForm(ActionEvent actionEvent) throws IOException {
        AddOrderController addOrderController = new AddOrderController();
        addOrderController.openPage();
        MainWindowController.stage.hide();
    }

    public void goAddProductForm(ActionEvent actionEvent) throws IOException {
        AddProductController addProductController = new AddProductController();
        addProductController.openPage();
        MainWindowController.stage.hide();
    }

    public void showProduct(ActionEvent actionEvent) {
        try {
            DBConnectionHead dbConnectionHead = new DBConnectionHead();
            dbConnectionHead.headOfficeConnection();
            titleProduct.setCellValueFactory(new PropertyValueFactory<>("title"));
            colorProduct.setCellValueFactory(new PropertyValueFactory<>("color"));
            thicknessProduct.setCellValueFactory(new PropertyValueFactory<>("thickness"));
            priceProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
            quantityProduct.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            dbConnectionHead.loadProductFromDB();
            productTable.setItems(productList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void showOrder(ActionEvent actionEvent) throws IOException {
        ShowOrderController showOrderController = new ShowOrderController();
        showOrderController.openPage();
        MainWindowController.stage.hide();
    }

    public void showInvoice(ActionEvent actionEvent) throws IOException {
        ShowInvoiceController showInvoiceController = new ShowInvoiceController();
        showInvoiceController.openPage();
        MainWindowController.stage.hide();
    }

    public void openPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Main_Window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("ЗМК");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);
    }
}
