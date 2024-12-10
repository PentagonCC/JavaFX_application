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
        int officeID = AuthController.getCurrentOffice();
        titleProduct.setCellValueFactory(new PropertyValueFactory<>("title"));
        colorProduct.setCellValueFactory(new PropertyValueFactory<>("color"));
        thicknessProduct.setCellValueFactory(new PropertyValueFactory<>("thickness"));
        priceProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityProduct.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        if (officeID == 3) {
            DBConnectionHead dbConnectionHead = new DBConnectionHead();
            dbConnectionHead.loadAddProductFromDB();
        } else if (officeID == 2) {
            DBConnectionDekabristov dbConnectionDekabristov = new DBConnectionDekabristov();
            dbConnectionDekabristov.loadAddProductFromDB();
        } else if (officeID == 1) {
            DBConnectionMilya dbConnectionMilya = new DBConnectionMilya();
            dbConnectionMilya.loadAddProductFromDB();
        }
        productTable.setItems(productList);
    }

    public void goBack(ActionEvent actionEvent) {
        MainWindowController.stage.show();
        AddProductController.stage.close();
    }

    public void addProduct(ActionEvent actionEvent) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        int officeID = AuthController.getCurrentOffice();
        if(!quantityField.getText().isEmpty()){
            int productID = getProductID();
            if(productID != 0) {
                int quantity = Integer.parseInt(quantityField.getText());
                if(officeID == 3) {
                    DBConnectionHead dbConnectionHead = new DBConnectionHead();
                    if (dbConnectionHead.addProduct(quantity, productID)) {
                        alert.setTitle("Успех");
                        alert.setHeaderText(null);
                        alert.setContentText("Товар добавлен!");
                        alert.showAndWait();
                    }
                }else if (officeID == 2){
                    DBConnectionDekabristov dbConnectionDekabristov = new DBConnectionDekabristov();
                    if(dbConnectionDekabristov.addProduct(quantity, productID)){
                        alert.setTitle("Успех");
                        alert.setHeaderText(null);
                        alert.setContentText("Товар добавлен!");
                        alert.showAndWait();
                    }
                }else if (officeID == 1){
                    DBConnectionMilya dbConnectionMilya = new DBConnectionMilya();
                    if(dbConnectionMilya.addProduct(quantity,productID)){
                        alert.setTitle("Успех");
                        alert.setHeaderText(null);
                        alert.setContentText("Товар добавлен!");
                        alert.showAndWait();
                    }
                }
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
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        int productID = 0;
        if (selectedProduct != null) {
            try {
                String title = selectedProduct.getTitle();
                String color = selectedProduct.getColor();
                double thickness = selectedProduct.getThickness();
                double price = selectedProduct.getPrice();
                PreparedStatement searchID = connection.prepareStatement("SELECT product_id FROM product " +
                        "WHERE color = ? AND price = ? AND thickness = ? AND title = ?");
                searchID.setString(4, title);
                searchID.setString(1, color);
                searchID.setDouble(3, thickness);
                searchID.setDouble(2, price);
                ResultSet id = searchID.executeQuery();
                if (id.next()) {
                    productID = id.getInt("product_id");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if(productID == 0) {
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Товар не найден");
                alert.showAndWait();
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
