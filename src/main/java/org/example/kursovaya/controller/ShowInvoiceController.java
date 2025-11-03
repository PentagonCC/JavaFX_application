package org.example.kursovaya.controller;

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
import org.example.kursovaya.*;
import org.example.kursovaya.dao.DBConnectionDekabristov;
import org.example.kursovaya.dao.DBConnectionHead;
import org.example.kursovaya.dao.DBConnectionMilya;
import org.example.kursovaya.model.Invoice;

import java.io.IOException;
import java.time.LocalDateTime;

public class ShowInvoiceController {

    static Stage stage = new Stage();
    
    @FXML
    public TableView<Invoice> invoiceTable;
    @FXML
    public TableColumn<Invoice, Double> totalPrice;
    @FXML
    public TableColumn<Invoice, Integer> numberOrder;
    @FXML
    public TableColumn<Invoice, LocalDateTime> dateTime;

    public static ObservableList<Invoice> invoiceList = FXCollections.observableArrayList();
    
    public void openPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Show-invoice.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        stage.setTitle("Накладные");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public void goBack(ActionEvent actionEvent) {
        MainWindowController.stage.show();
        ShowInvoiceController.stage.close();
    }

    public void showInvoice(ActionEvent actionEvent) {
        int officeId = AuthController.getCurrentOffice();
        numberOrder.setCellValueFactory(new PropertyValueFactory<>("numberOrder"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        dateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        if (officeId == 3) {
            DBConnectionHead dbConnectionHead = new DBConnectionHead();
            dbConnectionHead.loadInvoiceFromDB();
        } else if (officeId == 2) {
            DBConnectionDekabristov dbConnectionDekabristov = new DBConnectionDekabristov();
            dbConnectionDekabristov.loadInvoiceFromDB();
        } else if (officeId == 1) {
            DBConnectionMilya dbConnectionMilya = new DBConnectionMilya();
            dbConnectionMilya.loadInvoiceFromDB();
        }
        invoiceTable.setItems(invoiceList);
    }
}
