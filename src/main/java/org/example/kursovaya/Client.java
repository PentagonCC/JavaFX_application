package org.example.kursovaya;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Client {

    private final SimpleIntegerProperty clientID;
    private final SimpleStringProperty name;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty patronymic;
    private final SimpleStringProperty number;
    private final SimpleIntegerProperty officeID;

    public Client(String name, String surname, String patronymic, String number, int officeID, int clientID){
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.number = new SimpleStringProperty(number);
        this.officeID = new SimpleIntegerProperty(officeID);
        this.clientID = new SimpleIntegerProperty(clientID);
    }

    public String getName(){
        return name.get();
    }

    public SimpleStringProperty nameProperty(){
        return name;
    }

    public String getSurname(){
        return surname.get();
    }

    public SimpleStringProperty surnameProperty(){
        return surname;
    }

    public String getPatronymic(){
        return patronymic.get();
    }

    public SimpleStringProperty patronymicProperty(){
        return patronymic;
    }

    public String getNumber(){
        return number.get();
    }

    public SimpleStringProperty numberProperty(){
        return number;
    }

    public int getOfficeID(){
        return officeID.get();
    }

    public SimpleIntegerProperty officeIdProperty(){
        return officeID;
    }

    public int getClientID(){
        return clientID.get();
    }

    public SimpleIntegerProperty clientIdProperty(){
        return clientID;
    }
}
