package org.example.kursovaya;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.example.kursovaya.DBConnectionHead.*;

public class AddProductController {
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
    public TextField quantityField;

    public static ObservableList<Product> productList = FXCollections.observableArrayList();


    public void openPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Add-product.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Добавление товара");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }

    public void showProduct() {
        try {
            DBConnectionHead dbConnectionHead = new DBConnectionHead();
            dbConnectionHead.headOfficeConnection();
            titleProduct.setCellValueFactory(new PropertyValueFactory<>("title"));
            colorProduct.setCellValueFactory(new PropertyValueFactory<>("color"));
            thicknessProduct.setCellValueFactory(new PropertyValueFactory<>("thickness"));
            priceProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
            quantityProduct.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            dbConnectionHead.loadAddProductFromDB();
            productTable.setItems(productList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void goBack(ActionEvent actionEvent) {
        MainWindowController.stage.show();
        AddProductController.stage.close();
    }

    public void addProduct(ActionEvent actionEvent) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DBConnectionHead dbConnectionHead = new DBConnectionHead();
        if(!quantityField.getText().isEmpty()){
            int productID = getProductID();
            int quantity = Integer.parseInt(quantityField.getText());
            if(dbConnectionHead.addProduct(quantity, productID)){
                alert.setTitle("Успех");
                alert.setHeaderText(null);
                alert.setContentText("Товар добавлен!");
                alert.showAndWait();
                quantityField.clear();
            }
        }
        else {
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Введите количество");
            alert.showAndWait();
        }
    }

    public int getProductID () throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Connection connection = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
        int search = productTable.getSelectionModel().getSelectedIndex();
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        int productID = 0;
        if (selectedProduct != null) {
            try {
                PreparedStatement searchID = connection.prepareStatement("SELECT product_id FROM product WHERE product_id = ?");
                searchID.setInt(1, search);
                ResultSet id = searchID.executeQuery();
                if (id.next()) {
                    productID = id.getInt("product_id");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Выделите строку");
            alert.showAndWait();

        }
        return productID;
    }
}
