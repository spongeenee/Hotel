package com.example.hotel.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HabitacionesController implements Initializable {
    @FXML private GridPane gridHabitaciones;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        renderHabitaciones();
    }

    private void renderHabitaciones() {
        gridHabitaciones.getChildren().clear();

        String[] pisos = {"Piso 1", "Piso 2", "Piso 3", "Piso 4", "Piso 5"};

        for (int i = 0; i < pisos.length; i++) {
            Label piso = new Label(pisos[pisos.length - i - 1]);
            piso.setMaxWidth(Double.MAX_VALUE);
            piso.setAlignment(Pos.CENTER);
            gridHabitaciones.add(piso, 0, i);
        }

        int col = 1;
        int row = 0;
        int pisoIndex = 40;
        for (int i = 1; i <= 50; i++) {
            gridHabitaciones.add(celdaHabitacion(pisoIndex + col), col, row);
            if (++col == 11) {
                row++;
                col = 1;
                pisoIndex = pisoIndex - 10;
            }
        }
    }

    private VBox celdaHabitacion(int numero) {
        boolean ocupada = false;
        boolean fueraDeServicio = false;

        VBox celda = new VBox();
        celda.setPadding(new Insets(5));
        celda.setPrefSize(30, 40);
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
