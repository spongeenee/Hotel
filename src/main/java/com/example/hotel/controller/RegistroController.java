package com.example.hotel.controller;

import com.example.hotel.HelloApplication;
import com.example.hotel.models.Estado;
import com.example.hotel.models.Huesped;
import com.example.hotel.models.Reservacion;
import com.example.hotel.notreallymodels.Orientacion;
import com.example.hotel.service.ReservacionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class RegistroController implements Initializable {
    private final ReservacionService servicio = new ReservacionService();

    @FXML private TextField nombre;
    @FXML private TextField correo;
    @FXML private DatePicker fechaEntrada;
    @FXML private DatePicker fechaSalida;
    @FXML private ChoiceBox<Estado> estado;
    @FXML private GridPane habitacionPanel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarHabitaciones();
        ObservableList<Estado> estados = FXCollections.observableArrayList();
        estados.add(Estado.PENDIENTE);
        estados.add(Estado.CONFIRMADA);
        estado.setItems(estados);
        estado.setValue(Estado.PENDIENTE);
    }

    @FXML
    private void registrar(ActionEvent event) {
        Huesped huesped = new Huesped(0, nombre.getText(), correo.getText());
        Reservacion reservacion = new Reservacion.Builder()
                .huespedID(huesped.ID())
                .fechaIngreso(Date.valueOf(fechaEntrada.getValue()).toLocalDate())
                .fechaSalida(Date.valueOf(fechaSalida.getValue()).toLocalDate())
                .estado(estado.getValue())
                .build();
        servicio.registrar(reservacion);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void cargarHabitaciones() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("habitaciones.fxml")
            );
            Parent vista = loader.load();
            HabitacionesController controller = loader.getController();
            controller.settings(true, Orientacion.IZQUIERDA, 30);
            habitacionPanel.getChildren().setAll(vista);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
