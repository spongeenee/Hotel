package com.example.hotel.controller;

import com.example.hotel.HelloApplication;
import com.example.hotel.models.Estado;
import com.example.hotel.models.Habitacion;
import com.example.hotel.models.Reservacion;
import com.example.hotel.notreallymodels.KPIChartFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class RecepcionistaController {
    private long usuarioID;

    @FXML private GridPane contenedorHabitaciones;
    @FXML private StackPane contenedorCalendario;
    @FXML private StackPane menuContenedor;
    @FXML private VBox dashboard;
    @FXML private VBox reservacionPane;
    @FXML private VBox huespedesPane;
    @FXML private VBox KPIentradas;
    @FXML private VBox KPIsalidas;
    @FXML private VBox KPIrecientes;

    @FXML private TextField registroNombre;
    @FXML private TextField registroCorreo;
    @FXML private DatePicker registroDesde;
    @FXML private DatePicker registroHasta;
    @FXML private TableColumn<Reservacion, String> reservacionIDColumn;
    @FXML private TableColumn<Reservacion, String> reservacionCorreoColumn;
    @FXML private TableColumn<Reservacion, String> reservacionHabitacionColumn;
    @FXML private TableColumn<Reservacion, Estado> reservacionEstadoColumn;
    @FXML private TableColumn<Reservacion, LocalDateTime> reservacionRegistoColumn;
    @FXML private TableColumn<Reservacion, LocalDateTime> reservacionIngresoColumn;
    @FXML private TableColumn<Reservacion, LocalDateTime> reservacionSalidaColumn;
    @FXML private TableView<Reservacion> reservacionTableView;

    public void setUsuarioID(long usuarioID) {
        this.usuarioID = usuarioID;
    }



    @FXML
    protected void initialize() {
        menuContenedor.getChildren().setAll(dashboard);
        cargarCalendario();
        cargarHabitaciones();
        cargarGraphDashboard();
    }

    @FXML
    protected void mostrarReservacionPane() {
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(reservacionPane);
    }

    @FXML
    protected void mostrarHuespedesPane() {
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(huespedesPane);
    }

    @FXML
    protected void mostrarDashboardPane() {
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(dashboard);
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

    private void cargarHabitaciones() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("habitaciones.fxml")
            );
            Parent vista = loader.load();
            contenedorHabitaciones.getChildren().setAll(vista);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void cargarGraphDashboard() {
        KPIentradas.getChildren().add(
                KPIChartFactory.crearKPI("Entradas del dia", 10, 20));
        KPIsalidas.getChildren().add(
                KPIChartFactory.crearKPI("Salidas del día", 5, 15));
        KPIrecientes.getChildren().add(
                KPIChartFactory.crearKPI("Nuevas reservaciones", 1, 0));
    }
}
