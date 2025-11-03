module org.example.kursovaya {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires java.sql;

    opens org.example.kursovaya to javafx.fxml;
    exports org.example.kursovaya;
    exports org.example.kursovaya.controller;
    opens org.example.kursovaya.controller to javafx.fxml;
    exports org.example.kursovaya.dao;
    opens org.example.kursovaya.dao to javafx.fxml;
    exports org.example.kursovaya.model;
    opens org.example.kursovaya.model to javafx.fxml;
}