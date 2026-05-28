package com.example.hotel.controller;

import com.example.hotel.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SupervisorController {
    private long usuarioID;

    @FXML private DatePicker datePicker;
    @FXML private StackPane contenedorCalendario;
    @FXML private GridPane contenedorHabitaciones;
    @FXML private VBox dashboardPanel;
    @FXML private VBox reservacionPanel;
    @FXML private VBox habitacionesPanel;
    @FXML private VBox catalogosPanel;
    @FXML private VBox auditoriaPanel;
    @FXML private VBox usuariosPanel;
    @FXML private StackPane menuContenedor;

    public void setUsuarioID(long usuarioID) {
        this.usuarioID = usuarioID;
    }

    @FXML
    protected void initialize() {
        menuContenedor.getChildren().clear();
        cargarCalendario();
        cargarHabitaciones();
    }

    @FXML
    protected void mostrarDashboardPanel(){
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(dashboardPanel);
    }

    @FXML
    protected void mostrarReservacionesPanel() {
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(reservacionPanel);
    }

    @FXML
    protected void mostrarHabitacionesPanel(){
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(habitacionesPanel);
    }

    @FXML
    protected void mostrarCatalogosPanel(){
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(catalogosPanel);
    }

    @FXML
    protected void mostrarAuditoriaPanel(){
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(auditoriaPanel);
    }

    @FXML
    protected void mostrarUsuariosPanel(){
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(usuariosPanel);
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
}
