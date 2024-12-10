package org.example.kursovaya;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import static org.example.kursovaya.DBConnectionHead.*;

public class AddOrderController {
    static Stage stage = new Stage();

    @FXML
    public TableView<Client> clientTable;
    @FXML
    public TableColumn<Client, String> nameColumn;
    @FXML
    public TableColumn<Client, String> surnameColumn;
    @FXML
    public TableColumn<Client, String> patronymicColumn;
    @FXML
    public TableColumn<Client, Integer> officeColumn;
    @FXML
    public TableColumn<Client, String> numberColumn;
    @FXML
    public TableView<Product> productTable;
    @FXML
    public TableColumn<Client, String> titleProduct;
    @FXML
    public TableColumn<Client, String> colorProduct;
    @FXML
    public TableColumn<Client, String> thicknessProduct;
    @FXML
    public TableColumn<Client, String> priceProduct;
    @FXML
    public TableColumn<Client, String> quantityProduct;
    @FXML
    public TextField quantityField;
    @FXML
    public CheckBox inHeadOffice;

    public static ObservableList<Product> productList = FXCollections.observableArrayList();
    public static ObservableList<Client> clientList = FXCollections.observableArrayList();

    public void openPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Add-order.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Добавление заказа");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        if(AuthController.getCurrentOffice() == 3){
            inHeadOffice.setVisible(false);
        }
    }

    public void goBack(ActionEvent actionEvent) {
        MainWindowController.stage.show();
        AddOrderController.stage.close();
    }

    public void addOrder(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        int officeID = AuthController.getCurrentOffice();
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if(!quantityField.getText().isEmpty() && selectedProduct != null && selectedClient !=null) {
            int quantity = Integer.parseInt(quantityField.getText());
            if(quantity <= selectedProduct.getQuantity() || inHeadOffice.isSelected()) {
                String product = selectedProduct.getTitle() + " " + selectedProduct.getColor() + " " + selectedProduct.getThickness() + " " +
                        selectedProduct.getPrice() + " " + quantity;
                int clientId = selectedClient.getClientID();
                double price = selectedProduct.getPrice();
                try {
                    int productId = getProductID();
                    if (officeID == 3) {
                        DBConnectionHead dbConnectionHead = new DBConnectionHead();
                        int employeeId = DBConnectionHead.getCurrentEmployee();
                        if (dbConnectionHead.addOrder(officeID, clientId, employeeId, product, productId, quantity, price)) {
                            alert.setTitle("Успех");
                            alert.setHeaderText(null);
                            alert.setContentText("Заказ создан с одной позицией, можно добавлять товар еще!");
                            alert.showAndWait();
                        }
                        quantityField.clear();
                    } else if (officeID == 2) {
                        DBConnectionDekabristov dbConnectionDekabristov = new DBConnectionDekabristov();
                        int employeeId = DBConnectionDekabristov.getCurrentEmployee();
                        if(inHeadOffice.isSelected()){
                            DBConnectionHead dbConnectionHead = new DBConnectionHead();
                            if (dbConnectionHead.addOrder(officeID, clientId, employeeId, product, productId, quantity, price)) {
                                alert.setTitle("Успех");
                                alert.setHeaderText(null);
                                alert.setContentText("Заказ отправлен в головной офис, можно добавлять товар еще!");
                                alert.showAndWait();
                            }
                        }
                        else {
                            if (dbConnectionDekabristov.addOrder(officeID, clientId, employeeId, product, productId, quantity, price)) {
                                alert.setTitle("Успех");
                                alert.setHeaderText(null);
                                alert.setContentText("Заказ создан с одной позицией, можно добавлять товар еще!");
                                alert.showAndWait();
                            }
                        }
                        quantityField.clear();
                    } else if (officeID == 1) {
                        DBConnectionMilya dbConnectionMilya = new DBConnectionMilya();
                        int employeeId = DBConnectionMilya.getCurrentEmployee();
                        if(inHeadOffice.isSelected()){
                            DBConnectionHead dbConnectionHead = new DBConnectionHead();
                            if (dbConnectionHead.addOrder(officeID, clientId, employeeId, product, productId, quantity, price)) {
                                alert.setTitle("Успех");
                                alert.setHeaderText(null);
                                alert.setContentText("Заказ отправлен в головной офис, можно добавлять товар еще!");
                                alert.showAndWait();
                            }
                        }
                        else {
                            if (dbConnectionMilya.addOrder(officeID, clientId, employeeId, product, productId, quantity, price)) {
                                alert.setTitle("Успех");
                                alert.setHeaderText(null);
                                alert.setContentText("Заказ создан с одной позицией, можно добавлять товар еще!");
                                alert.showAndWait();
                            }
                        }
                        quantityField.clear();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            else {
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("На складе нет столько товара");
                alert.showAndWait();
            }
        }
        else {
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Проверьте введенность количества и выбранность клиента и товара");
            alert.showAndWait();
        }
    }

    public void updateOrder(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        int officeID = AuthController.getCurrentOffice();
        if(!quantityField.getText().isEmpty() && selectedProduct != null) {
            int quantity = Integer.parseInt(quantityField.getText());
            if(quantity <= selectedProduct.getQuantity()) {
                String product = selectedProduct.getTitle() + " " + selectedProduct.getColor() + " " + selectedProduct.getThickness() + " " +
                        selectedProduct.getPrice() + " " + quantity;
                double price = selectedProduct.getPrice();
                try {
                    int productId = getProductID();
                    if (officeID == 3) {
                        DBConnectionHead dbConnectionHead = new DBConnectionHead();
                        if (dbConnectionHead.updateOrder(product, productId, quantity, price)) {
                            alert.setTitle("Успех");
                            alert.setHeaderText(null);
                            alert.setContentText("Товар добавлен в заказ!");
                            alert.showAndWait();
                        }
                        quantityField.clear();
                    } else if (officeID == 2) {
                        DBConnectionDekabristov dbConnectionDekabristov = new DBConnectionDekabristov();
                        if (dbConnectionDekabristov.updateOrder(product, productId, quantity, price)) {
                            alert.setTitle("Успех");
                            alert.setHeaderText(null);
                            alert.setContentText("Товар добавлен в заказ!");
                            alert.showAndWait();
                        }
                        quantityField.clear();
                    } else if (officeID == 1) {
                        DBConnectionMilya dbConnectionMilya = new DBConnectionMilya();
                        if (dbConnectionMilya.updateOrder(product, productId, quantity, price)) {
                            alert.setTitle("Успех");
                            alert.setHeaderText(null);
                            alert.setContentText("Товар добавлен в заказ!");
                            alert.showAndWait();
                        }
                        quantityField.clear();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("На складе нет столько товара");
                alert.showAndWait();
            }
        }
        else {
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Проверьте введенность количества и выбранность товара");
            alert.showAndWait();
        }
    }

    public void showData(ActionEvent actionEvent) {
        int officeID = AuthController.getCurrentOffice();
        titleProduct.setCellValueFactory(new PropertyValueFactory<>("title"));
        colorProduct.setCellValueFactory(new PropertyValueFactory<>("color"));
        thicknessProduct.setCellValueFactory(new PropertyValueFactory<>("thickness"));
        priceProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityProduct.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        officeColumn.setCellValueFactory(new PropertyValueFactory<>("office_id"));
        if (officeID == 3) {
            DBConnectionHead dbConnectionHead = new DBConnectionHead();
            dbConnectionHead.loadAddProductOrderFromDB();
        } else if (officeID == 2) {
            DBConnectionDekabristov dbConnectionDekabristov = new DBConnectionDekabristov();
            dbConnectionDekabristov.loadAddProductOrderFromDB();
        } else if (officeID == 1) {
            DBConnectionMilya dbConnectionMilya = new DBConnectionMilya();
            dbConnectionMilya.loadAddProductOrderFromDB();
        }
        productTable.setItems(productList);
        clientTable.setItems(clientList);
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
