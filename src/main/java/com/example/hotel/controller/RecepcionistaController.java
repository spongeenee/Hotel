package com.example.hotel.controller;

import com.example.hotel.HelloApplication;
import com.example.hotel.models.*;
import com.example.hotel.notreallymodels.KPIChartFactory;
import com.example.hotel.notreallymodels.Orientacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class RecepcionistaController extends UsuarioController {
    private long usuarioID;

    @FXML private StackPane calendarioWidget;
    @FXML private StackPane menuContenedor;
    @FXML private GridPane habitacionesWidget;
    @FXML private VBox dashboard;
    @FXML private VBox reservacionPane;
    @FXML private VBox huespedesPane;
    @FXML private VBox KPIentradas;
    @FXML private VBox KPIsalidas;
    @FXML private VBox KPIrecientes;
    @FXML private Label encabezado;

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
        encabezado.setText("Bienvenido, " + usuarioID);
        cargarCalendario();
        cargarHabitaciones();
        cargarGraphDashboard();
    }

    @FXML
    protected void registrarReservacion() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("registro-view.fxml"));
        Parent root = loader.load();
        RegistroController controller = loader.getController();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Registro Reservacion");
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML
    protected void mostrarReservacionPane() {
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(reservacionPane);
        encabezado.setText("Registro de Reservaciones");
    }

    @FXML
    protected void mostrarHuespedesPane() {
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(huespedesPane);
        encabezado.setText("Registro de Habitaciones");
    }

    @FXML
    protected void mostrarDashboardPane() {
        menuContenedor.getChildren().clear();
        menuContenedor.getChildren().setAll(dashboard);
        encabezado.setText("Dashboard");
    }

    private void cargarCalendario() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("calendario.fxml")
            );
            Parent vista = loader.load();
            calendarioWidget.getChildren().setAll(vista);

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
            controller.settings(true, Orientacion.DERECHA);
            habitacionesWidget.getChildren().setAll(vista);
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
