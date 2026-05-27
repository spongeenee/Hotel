module com.example.hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.example.hotel to javafx.fxml;
    exports com.example.hotel;
    exports com.example.hotel.controller;
    opens com.example.hotel.controller to javafx.fxml;
}