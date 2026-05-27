package com.example.hotel.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;

public class CalendarioController implements Initializable {

    @FXML private GridPane gridCalendario;
    @FXML private Label lblMesAnio;
    @FXML private Label lblResumen;

    private YearMonth mesActual;

    // ── Modelo mínimo (sin importar tu clase real todavía) ───────────────────
    private record MockReservacion(LocalDateTime ingreso, LocalDateTime salida) {}
    private List<MockReservacion> reservaciones;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mesActual     = YearMonth.now();
        reservaciones = generarMock();
        renderCalendario();
    }

    // ── Navegación ───────────────────────────────────────────────────────────
    @FXML private void onMesAnterior()   { mesActual = mesActual.minusMonths(1); renderCalendario(); }
    @FXML private void onMesSiguiente()  { mesActual = mesActual.plusMonths(1);  renderCalendario(); }
    @FXML private void onAnioAnterior()  { mesActual = mesActual.minusYears(1);  renderCalendario(); }
    @FXML private void onAnioSiguiente() { mesActual = mesActual.plusYears(1);   renderCalendario(); }

    // ── Render principal ─────────────────────────────────────────────────────
    private void renderCalendario() {
        gridCalendario.getChildren().clear();

        // Encabezado mes / año
        String mes = mesActual.getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("es", "MX"));
        mes = mes.substring(0, 1).toUpperCase() + mes.substring(1);
        lblMesAnio.setText(mes + "  " + mesActual.getYear());

        // Encabezados días de la semana
        String[] dias = {"Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"};
        for (int i = 0; i < 7; i++) {
            Label h = new Label(dias[i]);
            h.setMaxWidth(Double.MAX_VALUE);
            h.setAlignment(Pos.CENTER);
            h.setFont(Font.font("System", FontWeight.BOLD, 12));
            h.setPadding(new Insets(4, 0, 6, 0));
            h.setTextFill(Color.web("#546e7a"));
            gridCalendario.add(h, i, 0);
        }

        // Celdas de días
        LocalDate primero = mesActual.atDay(1);

        int col = primero.getDayOfWeek().getValue();
        int row = 1;

        for (int d = 1; d <= mesActual.lengthOfMonth(); d++) {
            LocalDate fecha = mesActual.atDay(d);
            gridCalendario.add(celdaDia(fecha), col, row);
            if (++col == 7) { col = 0; row++; }
        }

        actualizarResumen();
    }

    private VBox celdaDia(LocalDate fecha) {
        boolean esHoy = fecha.equals(LocalDate.now());
        boolean esMes = fecha.getMonth() == mesActual.getMonth();

        long entradas = contarEntradas(fecha);
        long salidas = contarSalidas(fecha);
        long activas = contarActivas(fecha);

        VBox celda = new VBox(3);
        celda.setPadding(new Insets(5));
        celda.setPrefSize(30, 40);
        celda.setAlignment(Pos.TOP_CENTER);

        String dayFocus = "linear-gradient(#d8e1ff, #9db5ff)";
        String bg = esHoy ? dayFocus
                : (esMes ? "linear-gradient(#ececec, #cfcfcf)"
                         : "#fafafa");
        String bordePressed = "#1976d2;";
        String borde = esHoy ? "#1976d2" : "#a2a2a2";

        celda.setStyle(
                "-fx-background-color: " + bg + ";" +
                        "-fx-border-color: " + borde + ";" +
                        "-fx-border-width: " + (esHoy ? "1.5" : "1") + ";" +
                        "-fx-border-radius: 4;" +
                        "-fx-background-radius: 4;"
        );

        // Número del día
        Label numLabel = new Label(String.valueOf(fecha.getDayOfMonth()));
        numLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        numLabel.setTextFill(esHoy ? Color.web("#1565c0") : Color.web("#37474f"));
        celda.getChildren().add(numLabel);

        // Indicadores (solo si hay datos)
        if (activas > 0)
            celda.getChildren().add(chip(Long.toString(activas), "#455a64", "#eceff1"));
        if (entradas > 0)
            celda.getChildren().add(chip(Long.toString(entradas), "#2e7d32", "#e8f5e9"));
        if (salidas > 0)
            celda.getChildren().add(chip(Long.toString(salidas), "#b71c1c", "#ffebee"));

        celda.setOnMouseEntered( e ->
                celda.setStyle(celda.getStyle() + "-fx-cursor: hand")
                );
        celda.setOnMousePressed(e -> {
            celda.setStyle(celda.getStyle().replace(bg, esHoy ? "#bbdefb" : dayFocus));
            celda.setStyle(celda.getStyle().replace(borde, bordePressed));
        });
        celda.setOnMouseReleased(e -> {
            celda.setStyle(celda.getStyle().replace(esHoy ? "#bbdefb" : dayFocus, bg));
            celda.setStyle(celda.getStyle().replace(bordePressed, borde));
        });
        celda.setOnMouseClicked(e -> onDiaClick(fecha, entradas, salidas, activas));

        return celda;
    }

    private Label chip(String texto, String colorTexto, String colorFondo) {
        Label l = new Label(texto);
        l.setFont(Font.font("System", 10));
        l.setTextFill(Color.web(colorTexto));
        l.setPadding(new Insets(1, 4, 1, 4));
        l.setStyle(
                "-fx-background-color: " + colorFondo + ";" +
                        "-fx-background-radius: 4;"
        );
        return l;
    }

    // ── Click en un día ──────────────────────────────────────────────────────
    private void onDiaClick(LocalDate fecha, long entradas, long salidas, long activas) {
        // TODO: abrir ventana de detalle cuando el service esté listo
        System.out.printf("[Calendario] %s → activas=%d  entradas=%d  salidas=%d%n",
                fecha, activas, entradas, salidas);
    }

    // ── Resumen del mes en el label inferior ─────────────────────────────────
    private void actualizarResumen() {
        long totalEntradas = reservaciones.stream()
                .filter(r -> YearMonth.from(r.ingreso()).equals(mesActual))
                .count();
        long totalSalidas = reservaciones.stream()
                .filter(r -> YearMonth.from(r.salida()).equals(mesActual))
                .count();
        lblResumen.setText(
                "Este mes: " + totalEntradas + " llegada(s)  ·  " + totalSalidas + " salida(s)"
        );
    }
    private long contarEntradas(LocalDate f) {
        return reservaciones.stream()
                .filter(r -> r.ingreso().toLocalDate().equals(f)).count();
    }
    private long contarSalidas(LocalDate f) {
        return reservaciones.stream()
                .filter(r -> r.salida().toLocalDate().equals(f)).count();
    }
    private long contarActivas(LocalDate f) {
        return reservaciones.stream()
                .filter(r -> !f.isBefore(r.ingreso().toLocalDate())
                        && !f.isAfter(r.salida().toLocalDate()))
                .count();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MOCK — reemplazar por llamada al service cuando esté disponible
    //  reservaciones = ReservacionService.getInstance().getAll();
    // ════════════════════════════════════════════════════════════════════════
    private List<MockReservacion> generarMock() {
        List<MockReservacion> list = new ArrayList<>();
        LocalDate base = mesActual.atDay(1);

        list.add(new MockReservacion(base.plusDays(1).atStartOfDay(),  base.plusDays(4).atStartOfDay()));
        list.add(new MockReservacion(base.plusDays(3).atStartOfDay(),  base.plusDays(8).atStartOfDay()));
        list.add(new MockReservacion(base.plusDays(5).atStartOfDay(),  base.plusDays(7).atStartOfDay()));
        list.add(new MockReservacion(base.plusDays(10).atStartOfDay(), base.plusDays(15).atStartOfDay()));
        list.add(new MockReservacion(base.plusDays(12).atStartOfDay(), base.plusDays(14).atStartOfDay()));
        list.add(new MockReservacion(base.plusDays(14).atStartOfDay(), base.plusDays(20).atStartOfDay()));
        list.add(new MockReservacion(base.plusDays(18).atStartOfDay(), base.plusDays(22).atStartOfDay()));
        list.add(new MockReservacion(base.plusDays(20).atStartOfDay(), base.plusDays(26).atStartOfDay()));
        list.add(new MockReservacion(base.plusDays(24).atStartOfDay(), base.plusDays(28).atStartOfDay()));

        return list;
    }
}