package org.example.kursovaya.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {

    private final SimpleIntegerProperty orderId;
    private final SimpleIntegerProperty officeId;
    private final SimpleIntegerProperty clientId;
    private final SimpleIntegerProperty employeeId;
    private final SimpleStringProperty products;
    private final SimpleStringProperty fioEmployee;
    private final SimpleStringProperty officeAddress;
    private final SimpleStringProperty fioClient;

    public Order(int orderId, int officeId, int clientId, int employeeId, String products) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.officeId = new SimpleIntegerProperty(officeId);
        this.clientId = new SimpleIntegerProperty(clientId);
        this.employeeId = new SimpleIntegerProperty(employeeId);
        this.products = new SimpleStringProperty(products);
        this.fioEmployee = new SimpleStringProperty("");
        this.officeAddress = new SimpleStringProperty("");
        this.fioClient = new SimpleStringProperty("");
    }

    public Order(int orderId, String fioEmployee, String officeAddress, String fioClient, String products) {
        this.officeId = new SimpleIntegerProperty(0);
        this.clientId = new SimpleIntegerProperty(0);
        this.employeeId = new SimpleIntegerProperty(0);
        this.orderId = new SimpleIntegerProperty(orderId);
        this.products = new SimpleStringProperty(products);
        this.fioEmployee = new SimpleStringProperty(fioEmployee);
        this.officeAddress = new SimpleStringProperty(officeAddress);
        this.fioClient = new SimpleStringProperty(fioClient);
    }

    public String getFIOEmployee(){
        return fioEmployee.get();
    }

    public SimpleStringProperty fioEmployeeProperty(){
        return  fioEmployee;
    }

    public String getOfficeAddress(){
        return officeAddress.get();
    }

    public SimpleStringProperty officeAddressProperty(){
        return  officeAddress;
    }

    public String getFIOClient(){
        return fioClient.get();
    }

    public SimpleStringProperty fioClientProperty(){
        return fioClient;
    }

    public int getOrderId(){
        return orderId.get();
    }

    public SimpleIntegerProperty orderIdProperty(){
        return orderId;
    }

    public int getOfficeId(){
        return officeId.get();
    }

    public SimpleIntegerProperty officeIdProperty(){
        return officeId;
    }

    public int getClientId(){
        return clientId.get();
    }

    public SimpleIntegerProperty clientIdProperty(){
        return clientId;
    }

    public int getEmployeeId(){
        return employeeId.get();
    }

    public SimpleIntegerProperty employeeIdProperty(){
        return employeeId;
    }

    public String getProducts() {
        return products.get();
    }

    public SimpleStringProperty productsProperty(){
        return products;
    }
}
