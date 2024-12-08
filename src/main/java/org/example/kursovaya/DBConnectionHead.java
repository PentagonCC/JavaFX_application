package org.example.kursovaya;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBConnectionHead {

    static final String DB_URL_HEAD = "jdbc:postgresql://localhost:5432/headOffice";
    static final String USER = "postgres";
    static final String PASS = "1234";
    static Connection connection;

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
            if(emailFieldText.equals(email) &&  passwordFieldText.equals(password) && id == 3){
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
            Statement getQuantity = connection.createStatement();
            ResultSet resultSetProduct = getProduct.executeQuery("SELECT color, thickness, price, title FROM product");
            ResultSet resultSetQuantity = getQuantity.executeQuery("SELECT quantity FROM warehouse");
            while (resultSetProduct.next() && resultSetQuantity.next()){
                String title = resultSetProduct.getString("title");
                String color = resultSetProduct.getString("color");
                double thickness = resultSetProduct.getDouble("thickness");
                double price = resultSetProduct.getDouble("price");
                int quantity = resultSetQuantity.getInt("quantity");
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
            Statement getQuantity = connection.createStatement();
            ResultSet resultSetProduct = getProduct.executeQuery("SELECT color, thickness, price, title FROM product");
            ResultSet resultSetQuantity = getQuantity.executeQuery("SELECT quantity FROM warehouse");
            while (resultSetProduct.next() && resultSetQuantity.next()){
                String title = resultSetProduct.getString("title");
                String color = resultSetProduct.getString("color");
                double thickness = resultSetProduct.getDouble("thickness");
                double price = resultSetProduct.getDouble("price");
                int quantity = resultSetQuantity.getInt("quantity");
                AddProductController.productList.add(new Product(title, color, thickness, price, quantity));
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
}
