package com.example.hotel.controller;

import com.example.hotel.HelloApplication;
import com.example.hotel.models.Reservacion;
import com.example.hotel.notreallymodels.Orientacion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistroController implements Initializable {
    private Reservacion reservacion = null;

    @FXML private TextField nombre;
    @FXML private TextField correo;
    @FXML private DatePicker fechaEntrada;
    @FXML private DatePicker fechaSalida;
    @FXML private GridPane habitacionPanel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarHabitaciones();
    }

    @FXML
    private void confirmar() {
    }

    public Reservacion reservacion() {
        return reservacion;
    }

    private void cargarHabitaciones() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("habitaciones.fxml")
            );
            Parent vista = loader.load();
            HabitacionesController controller = loader.getController();
            controller.settings(true, Orientacion.ABAJO, 20);
            habitacionPanel.getChildren().setAll(vista);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
