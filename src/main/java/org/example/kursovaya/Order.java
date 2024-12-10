package org.example.kursovaya;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {

    private final SimpleIntegerProperty orderId;
    private final SimpleIntegerProperty officeId;
    private final SimpleIntegerProperty clientId;
    private final SimpleIntegerProperty employeeId;
    private final SimpleStringProperty products;

    public Order(int orderId, int officeId, int clientId, int employeeId, String products){
        this.orderId = new SimpleIntegerProperty(orderId);
        this.officeId = new SimpleIntegerProperty(officeId);
        this.clientId = new SimpleIntegerProperty(clientId);
        this.employeeId = new SimpleIntegerProperty(employeeId);
        this.products = new SimpleStringProperty(products);
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
