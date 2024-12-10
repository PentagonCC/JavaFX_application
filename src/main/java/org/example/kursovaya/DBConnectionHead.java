package org.example.kursovaya;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBConnectionHead {

    static final String DB_URL_HEAD = "jdbc:postgresql://localhost:5432/headOffice";
    static final String USER = "postgres";
    static final String PASS = "1234";
    static Connection connection;

    private static int currentEmployee;
    private static int currentOrder;

    public static int getCurrentOrder(){
        return currentOrder;
    }

    public static int getCurrentEmployee() {
        return currentEmployee;
    }

    public void headOfficeConnection () throws SQLException {
        connection = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
    }

    public boolean checkEmployee(String emailFieldText, String passwordFieldText) throws SQLException {
        connection = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");
        boolean checkFlag = false;
        while (resultSet.next()){
            int id = resultSet.getInt("office_id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("passwordemployee");
            if(emailFieldText.equals(email) && passwordFieldText.equals(password) && id == 3){
                currentEmployee = resultSet.getInt("employee_id");
                checkFlag =true;
            }
        }
        connection.close();
        return checkFlag;
    }

    public void loadProductFromDB() {
        try {
            MainWindowController.productList.clear();
            connection = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
            Statement getProduct = connection.createStatement();
            ResultSet resultSetProduct = getProduct.executeQuery("SELECT p.color, p.thickness, p.price, p.title, w.quantity " +
                    "FROM product p " +
                    "JOIN warehouse w ON p.product_id = w.product_id");
            while (resultSetProduct.next()){
                String title = resultSetProduct.getString("title");
                String color = resultSetProduct.getString("color");
                double thickness = resultSetProduct.getDouble("thickness");
                double price = resultSetProduct.getDouble("price");
                int quantity = resultSetProduct.getInt("quantity");
                MainWindowController.productList.add(new Product(title, color, thickness, price, quantity));
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAddProductFromDB() {
        try {
            AddProductController.productList.clear();
            connection = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
            Statement getProduct = connection.createStatement();
            ResultSet resultSetProduct = getProduct.executeQuery("SELECT p.color, p.thickness, p.price, p.title, w.quantity " +
                    "FROM product p " +
                    "JOIN warehouse w ON p.product_id = w.product_id");
            while (resultSetProduct.next()){
                String title = resultSetProduct.getString("title");
                String color = resultSetProduct.getString("color");
                double thickness = resultSetProduct.getDouble("thickness");
                double price = resultSetProduct.getDouble("price");
                int quantity = resultSetProduct.getInt("quantity");
                AddProductController.productList.add(new Product(title, color, thickness, price, quantity));
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAddProductOrderFromDB() {
        try {
            AddOrderController.productList.clear();
            AddOrderController.clientList.clear();
            connection = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
            Statement getProduct = connection.createStatement();
            Statement getClient = connection.createStatement();
            ResultSet resultSetProduct = getProduct.executeQuery("SELECT p.color, p.thickness, p.price, p.title, w.quantity " +
                    "FROM product p " +
                    "JOIN warehouse w ON p.product_id = w.product_id");
            ResultSet resultSetClient = getClient.executeQuery("SELECT * FROM client");
            while (resultSetProduct.next()){
                String title = resultSetProduct.getString("title");
                String color = resultSetProduct.getString("color");
                double thickness = resultSetProduct.getDouble("thickness");
                double price = resultSetProduct.getDouble("price");
                int quantity = resultSetProduct.getInt("quantity");
                AddOrderController.productList.add(new Product(title, color, thickness, price, quantity));
            }
            while(resultSetClient.next()){
                String name = resultSetClient.getString("nameclient");
                String surname = resultSetClient.getString("surname");
                String patronymic = resultSetClient.getString("patronymic");
                String number = resultSetClient.getString("numberclient");
                int officeID = resultSetClient.getInt("office_id");
                int clientID = resultSetClient.getInt("client_id");
                AddOrderController.clientList.add(new Client(name, surname, patronymic, number, officeID, clientID));
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addClient(String surname, String name, String patronymic, String number){
        boolean flag = false;
        try {
            connection = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
            if(phoneValidation(number)){
                PreparedStatement addClient = connection.prepareStatement("INSERT INTO client (office_id, nameclient, surname, patronymic, numberclient) VALUES (3, ?, ?, ?, ?)");
                addClient.setString(1, name);
                addClient.setString(2, surname);
                addClient.setString(3, patronymic);
                addClient.setString(4, number);
                int countRow = addClient.executeUpdate();
                flag = true;
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Введите корректный номер");
                alert.showAndWait();
                flag = false;
            }
            connection.close();

        }catch (SQLException e){
            throw new RuntimeException(e);

        }
        return flag;
    }

    public boolean addProduct(int quantity, int productID){
        boolean flag = false;
        try {
            connection = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
            PreparedStatement addProduct = connection.prepareStatement("UPDATE warehouse SET quantity = quantity + ? WHERE product_id = ?");
            addProduct.setInt(1, quantity);
            addProduct.setInt(2, productID);
            int upRow = addProduct.executeUpdate();
            flag = true;
            connection.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return flag;
    }

    private static boolean phoneValidation(String number){
        String regex = "^8\\d{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public boolean addOrder(int officeId, int clientId, int employeeId, String product, int productId, int quantity) {
        boolean flag = false;
        String jsonProduct = "\"" + product + "\"";
        try {
            connection = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
            PreparedStatement addOrder = connection.prepareStatement("INSERT INTO orders (office_id, client_id, employee_id, products) VALUES (?, ?, ?, ?::jsonb) RETURNING order_id ");
            addOrder.setInt(1, officeId);
            addOrder.setInt(2, clientId);
            addOrder.setInt(3, employeeId);
            addOrder.setString(4, jsonProduct);
            ResultSet resultSetId = addOrder.executeQuery();
            if(resultSetId.next()){
               currentOrder = resultSetId.getInt("order_id");
            }
            PreparedStatement updateQuantity = connection.prepareStatement("UPDATE warehouse SET quantity = quantity - ? WHERE product_id = ?");
            updateQuantity.setInt(1, quantity);
            updateQuantity.setInt(2, productId);
            int upRow = updateQuantity.executeUpdate();
            flag = true;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return flag;
    }

    public boolean updateOrder(String product, int productId, int quantity) {
        boolean flag = false;
        String jsonProduct = "\"" + product + "\"";
        try{
            connection = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
            PreparedStatement updateOrder = connection.prepareStatement("UPDATE orders SET products = products || ?::jsonb WHERE order_id = ?");
            updateOrder.setInt(2, currentOrder);
            updateOrder.setString(1, jsonProduct);
            PreparedStatement updateQuantity = connection.prepareStatement("UPDATE warehouse SET quantity = quantity - ? WHERE product_id = ?");
            updateQuantity.setInt(1, quantity);
            updateQuantity.setInt(2, productId);
            int upRow = updateQuantity.executeUpdate();
            int countRow = updateOrder.executeUpdate();
            flag = true;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return flag;
    }
}
