package com.example.hotel.controller;

import com.example.hotel.notreallymodels.Orientacion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class HabitacionesController implements Initializable {
    private long cellSize = 40;
    private boolean labeled;
    private Orientacion orientacion;
    private final String[] pisos = {"P1", "P2", "P3", "P4", "P5"};
    @FXML private GridPane gridHabitaciones;
    @FXML private HBox labelsNorte;
    @FXML private HBox labelsSur;
    @FXML private VBox labelsEste;
    @FXML private VBox labelsOeste;

    public void settings(boolean labeled) {
        this.labeled = labeled;
        this.orientacion = Orientacion.IZQUIERDA;
    }

    public void settings(boolean labeled, long cellSize) {
        this.cellSize = cellSize;
        this.labeled = labeled;
        this.orientacion = Orientacion.IZQUIERDA;
    }

    public void settings(boolean labeled, Orientacion orientacion) {
        this.labeled = labeled;
        this.orientacion = orientacion;
    }

    public void settings(boolean labeled, Orientacion orientacion, long cellSize) {
        this.cellSize = cellSize;
        this.labeled = labeled;
        this.orientacion = orientacion;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        renderHabitaciones();
    }

    private void renderHabitaciones() {
        gridHabitaciones.getChildren().clear();

        if (labeled) {
            for (int i = 0; i < pisos.length; i++) {
                Label piso = new Label(pisos[pisos.length - i - 1]);
                piso.setMaxWidth(Double.MAX_VALUE);
                piso.setAlignment(Pos.CENTER);
                switch (orientacion) {
                    case ARRIBA -> labelsNorte.getChildren().add(piso);
                    case ABAJO -> labelsSur.getChildren().add(piso);
                    case IZQUIERDA -> labelsOeste.getChildren().add(piso);
                    case DERECHA -> labelsEste.getChildren().add(piso);
                }
            }
        }

        int col = 0;
        int row = 0;
        int pisoIndex = 40;
        for (int i = 1; i <= 50; i++) {
            gridHabitaciones.add(celdaHabitacion(pisoIndex + col), col, row);
            if (++col == 10) {
                row++;
                col = 0;
                pisoIndex = pisoIndex - 10;
            }
        }
    }

    private VBox celdaHabitacion(int numero) {
        boolean ocupada = false;
        boolean fueraDeServicio = false;

        VBox celda = new VBox();
        celda.setPadding(new Insets(5));
        celda.setPrefSize(cellSize, cellSize);
        celda.setAlignment(Pos.TOP_CENTER);

        String bg = ocupada ? "linear-gradient(#F7BC63, #FFEED1)"
                : fueraDeServicio ? "linear-gradient(#CC2323, #FFDDAD)"
                : "linear-gradient(#ececec, #cfcfcf)";

        String borde = "#a2a2a2";

        celda.setStyle(
                "-fx-background-color: " + bg + ";" +
                "-fx-border-color: " + borde + ";" +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 4;" +
                "-fx-border-style: solid inside;" +
                "-fx-background-radius: 4"
        );

        celda.setOnMousePressed(e ->
            celda.setStyle(celda.getStyle().replace(bg,"linear-gradient(#d8e1ff, #9db5ff)"))
        );
        celda.setOnMouseReleased(e ->
            celda.setStyle(celda.getStyle().replace("linear-gradient(#d8e1ff, #9db5ff)", bg))
        );

        Label numLabel = new Label(String.valueOf(numero));
        celda.getChildren().add(numLabel);

        return celda;
    }

}
