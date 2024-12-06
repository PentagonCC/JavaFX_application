package org.example.kursovaya;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {

    private final SimpleStringProperty title;
    private final SimpleStringProperty color;
    private final SimpleDoubleProperty thickness;
    private final SimpleDoubleProperty price;
    private final SimpleIntegerProperty quantity;

    public Product(String title, String color, double thickness, double price, int quantity) {
        this.title = new SimpleStringProperty(title);
        this.color = new SimpleStringProperty(color);
        this.thickness = new SimpleDoubleProperty(thickness);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty (){
        return title;
    }

    public String getColor() {
        return color.get();
    }

    public SimpleStringProperty colorProperty() {
        return color;
    }

    public double getThickness() {
        return thickness.get();
    }

    public SimpleDoubleProperty thicknessProperty() {
        return thickness;
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }
}
