package com.example.hotel.controller;

import com.example.hotel.HelloApplication;
import com.example.hotel.adapters.ReservacionAdapter;
import com.example.hotel.models.*;
import com.example.hotel.notreallymodels.KPIChartFactory;
import com.example.hotel.notreallymodels.Orientacion;
import com.example.hotel.service.ReservacionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private final ReservacionService reservacionService =  new ReservacionService();
    private ObservableList<ReservacionAdapter> reservaciones =  FXCollections.observableArrayList();


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
    @FXML private TableColumn<ReservacionAdapter, Long> reservacionIDColumn;
    @FXML private TableColumn<ReservacionAdapter, String> reservacionCorreoColumn;
    @FXML private TableColumn<ReservacionAdapter, Long> reservacionHabitacionColumn;
    @FXML private TableColumn<ReservacionAdapter, String> reservacionEstadoColumn;
    @FXML private TableColumn<ReservacionAdapter, LocalDateTime> reservacionRegistoColumn;
    @FXML private TableColumn<ReservacionAdapter, LocalDateTime> reservacionIngresoColumn;
    @FXML private TableColumn<ReservacionAdapter, LocalDateTime> reservacionSalidaColumn;
    @FXML private TableView<ReservacionAdapter> reservacionTableView;

    public void setUsuarioID(long usuarioID) {
        this.usuarioID = usuarioID;
    }

    @FXML
    protected void initialize() {
        reservaciones = FXCollections.observableArrayList(reservacionService.reservacionAAdapter(reservacionService.listar()));

        reservacionIDColumn.setCellValueFactory(c -> c.getValue().IDProperty());
        reservacionCorreoColumn.setCellValueFactory(c -> c.getValue().huespedMailProperty());
        reservacionHabitacionColumn.setCellValueFactory(c -> c.getValue().habitacionIDProperty());
        reservacionEstadoColumn.setCellValueFactory(c -> c.getValue().estadoProperty());
        reservacionRegistoColumn.setCellValueFactory(c -> c.getValue().fechaRegistroProperty());
        reservacionIngresoColumn.setCellValueFactory(c -> c.getValue().fechaIngresoProperty());
        reservacionSalidaColumn.setCellValueFactory(c -> c.getValue().fechaSalidaProperty());

        reservacionTableView.setItems(reservaciones);

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
