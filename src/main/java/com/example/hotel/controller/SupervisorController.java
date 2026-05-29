package com.example.hotel.controller;

import com.example.hotel.HelloApplication;
import com.example.hotel.notreallymodels.Orientacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SupervisorController extends UsuarioController {
    private long usuarioID;

    @FXML private Label encabezado;
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
        encabezado.setText("Bienvenido, " + usuarioID);
    }

    @FXML
    protected void mostrarDashboardPanel(){
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(dashboardPanel);
        encabezado.setText("Dashboard");
    }

    @FXML
    protected void mostrarReservacionesPanel() {
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(reservacionPanel);
        encabezado.setText("Gestionar reservaciones");
    }

    @FXML
    protected void mostrarHabitacionesPanel(){
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(habitacionesPanel);
        encabezado.setText("Gestionar habitaciones");
    }

    @FXML
    protected void mostrarCatalogosPanel(){
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(catalogosPanel);
        encabezado.setText("Gestionar catalogos");
    }

    @FXML
    protected void mostrarAuditoriaPanel(){
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(auditoriaPanel);
        encabezado.setText("Auditoria");
    }

    @FXML
    protected void mostrarUsuariosPanel(){
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(usuariosPanel);
        encabezado.setText("Usuarios");
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
            HabitacionesController controller = loader.getController();
            controller.settings(true, Orientacion.IZQUIERDA);
            contenedorHabitaciones.getChildren().setAll(vista);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
