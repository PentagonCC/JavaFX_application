package org.example.kursovaya;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {

    static Stage stage = new Stage();

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
        stage.setResizable(false);
    }
}
