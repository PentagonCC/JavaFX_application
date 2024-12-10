package org.example.kursovaya;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDateTime;

public class Invoice {
    private final SimpleIntegerProperty numberOrder;
    private final SimpleDoubleProperty totalPrice;
    private final SimpleObjectProperty<LocalDateTime> dateTime;

    public Invoice(int numberOrder, double totalPrice, LocalDateTime dateTime){
        this.numberOrder = new SimpleIntegerProperty(numberOrder);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.dateTime = new SimpleObjectProperty<>(dateTime);
    }

    public int getNumberOrder(){
        return numberOrder.get();
    }

    public SimpleIntegerProperty numberOrderProperty(){
        return numberOrder;
    }

    public double getTotalPrice(){
        return totalPrice.get();
    }

    public SimpleDoubleProperty totalPriceProperty(){
        return totalPrice;
    }

    public LocalDateTime getDateTime(){
        return dateTime.get();
    }

    public SimpleObjectProperty<LocalDateTime> dateTimeProperty(){
        return dateTime;
    }

}
