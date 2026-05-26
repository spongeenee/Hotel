package com.example.hotel.controller;

import com.example.hotel.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalTime;

public class HelloController {
    @FXML private Label date;
    @FXML private DatePicker datePicker;
    @FXML private StackPane contenedorCalendario;
    @FXML private StackPane menuContenedor;
    @FXML private VBox dashboard;
    @FXML private VBox reservacionPane;
    @FXML private VBox huespedesPane;

    @FXML
    protected void showDatePicker() {
        date.setText(datePicker.getValue().atTime(LocalTime.NOON).toString());
    }

    @FXML
    protected void initialize() {
        menuContenedor.getChildren().setAll(dashboard);
        cargarCalendario();
    }

    @FXML
    protected void mostrarReservacionPane() {
        menuContenedor.getChildren().setAll(reservacionPane);
    }

    @FXML
    protected void mostrarHuespedesPane() {
        menuContenedor.getChildren().setAll(huespedesPane);
    }

    private void cargarCalendario() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("calendario.fxml")
            );
            Parent vista = loader.load();
            contenedorCalendario.getChildren().setAll(vista);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
