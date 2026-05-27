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

public class SupervisorController {
    @FXML private DatePicker datePicker;
    @FXML private StackPane contenedorCalendario;
    @FXML private StackPane menuContenedor;


    @FXML
    protected void initialize() {
        cargarCalendario();
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
