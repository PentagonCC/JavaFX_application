package org.example.kursovaya;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBConnectionDekabristov {

    static final String DB_URL_HEAD = "jdbc:postgresql://localhost:5432/headOffice";
    static final String DB_URL_DEKABRISTOV = "jdbc:postgresql://localhost:5432/dekabristov";
    static final String USER = "postgres";
    static final String PASS = "1234";
    static Connection connection;
    static Connection connectionMain;
    private static int currentEmployee;
    private static int currentOrder;

    public static int getCurrentOrder(){
        return currentOrder;
    }

    public static int getCurrentEmployee() {
        return currentEmployee;
    }

    public void dekabristovDBConnection () throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL_DEKABRISTOV, USER, PASS);
        Connection connection1 = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
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
            if(emailFieldText.equals(email) && passwordFieldText.equals(password) && id == 2){
                currentEmployee = resultSet.getInt("employee_id");
                checkFlag =true;
            }
        }
        connection.close();
        return checkFlag;
    }

    public boolean addClient(String surname, String name, String patronymic, String number){
        boolean flag = false;
        try {
            connection = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
            if(phoneValidation(number)){
                PreparedStatement addClient = connection.prepareStatement("INSERT INTO client (office_id, nameclient, surname, patronymic, numberclient) VALUES (2, ?, ?, ?, ?)");
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

    private static boolean phoneValidation(String number){
        String regex = "^8\\d{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public void loadProductFromDB() {
        try {
            MainWindowController.productList.clear();
            connection = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
            connectionMain = DriverManager.getConnection(DB_URL_DEKABRISTOV, USER, PASS);
            Statement getProduct = connection.createStatement();
            ResultSet resultSetProduct = getProduct.executeQuery("SELECT product_id, color, thickness, price, title FROM product");
            while (resultSetProduct.next()){
                int product_id = resultSetProduct.getInt("product_id");
                String title = resultSetProduct.getString("title");
                String color = resultSetProduct.getString("color");
                double thickness = resultSetProduct.getDouble("thickness");
                double price = resultSetProduct.getDouble("price");
                PreparedStatement getQuantity = connectionMain.prepareStatement("SELECT quantity FROM warehouse WHERE product_id = ?");
                getQuantity.setInt(1, product_id);
                ResultSet resultSetQuantity = getQuantity.executeQuery();
                while (resultSetQuantity.next()) {
                    int quantity = resultSetQuantity.getInt("quantity");
                    MainWindowController.productList.add(new Product(title, color, thickness, price, quantity));
                }
            }
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadAddProductFromDB() {
        try {
            AddProductController.productList.clear();
            AddOrderController.clientList.clear();
            connection = DriverManager.getConnection(DB_URL_HEAD, USER, PASS);
            connectionMain = DriverManager.getConnection(DB_URL_DEKABRISTOV, USER, PASS);
            Statement getProduct = connection.createStatement();
            ResultSet resultSetProduct = getProduct.executeQuery("SELECT product_id, color, thickness, price, title FROM product");
            while (resultSetProduct.next()){
                int product_id = resultSetProduct.getInt("product_id");
                String title = resultSetProduct.getString("title");
                String color = resultSetProduct.getString("color");
                double thickness = resultSetProduct.getDouble("thickness");
                double price = resultSetProduct.getDouble("price");
                PreparedStatement getQuantity = connectionMain.prepareStatement("SELECT quantity FROM warehouse WHERE product_id = ?");
                getQuantity.setInt(1, product_id);
                ResultSet resultSetQuantity = getQuantity.executeQuery();
                while (resultSetQuantity.next()) {
                    int quantity = resultSetQuantity.getInt("quantity");
                    AddProductController.productList.add(new Product(title, color, thickness, price, quantity));
                }
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
            connectionMain = DriverManager.getConnection(DB_URL_DEKABRISTOV, USER, PASS);
            Statement getProduct = connection.createStatement();
            Statement getClient = connection.createStatement();
            ResultSet resultSetClient = getClient.executeQuery("SELECT * FROM client");
            ResultSet resultSetProduct = getProduct.executeQuery("SELECT product_id, color, thickness, price, title FROM product");
            while (resultSetProduct.next()){
                int product_id = resultSetProduct.getInt("product_id");
                String title = resultSetProduct.getString("title");
                String color = resultSetProduct.getString("color");
                double thickness = resultSetProduct.getDouble("thickness");
                double price = resultSetProduct.getDouble("price");
                PreparedStatement getQuantity = connectionMain.prepareStatement("SELECT quantity FROM warehouse WHERE product_id = ?");
                getQuantity.setInt(1, product_id);
                ResultSet resultSetQuantity = getQuantity.executeQuery();
                while (resultSetQuantity.next()) {
                    int quantity = resultSetQuantity.getInt("quantity");
                    AddOrderController.productList.add(new Product(title, color, thickness, price, quantity));
                }
            }
            while (resultSetClient.next()){
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

    public boolean addProduct(int quantity, int productID){
        boolean flag = false;
        try {
            connection = DriverManager.getConnection(DB_URL_DEKABRISTOV, USER, PASS);
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


    public boolean addOrder(int officeId, int clientId, int employeeId, String product, int productId, int quantity) {
        boolean flag = false;
        String jsonProduct = "\"" + product + "\"";
        try {
            connection = DriverManager.getConnection(DB_URL_DEKABRISTOV, USER, PASS);
            PreparedStatement addOrder = connection.prepareStatement("INSERT INTO orders (office_id, client_id, employee_id, products) VALUES (?, ?, ?, ?::jsonb) RETURNING order_id");
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
            connection = DriverManager.getConnection(DB_URL_DEKABRISTOV, USER, PASS);
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
