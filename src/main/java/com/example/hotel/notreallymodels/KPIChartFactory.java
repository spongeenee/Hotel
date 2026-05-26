package com.example.hotel.notreallymodels;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.Map;

public class KPIChartFactory {
    public static PieChart crearKPI(String titulo, int atendidos, int total) {
        ObservableList<PieChart.Data> slice = FXCollections.observableArrayList(
            new PieChart.Data(titulo, atendidos),
            new PieChart.Data("Total por hoy", total != 0 ? total - atendidos : 0)
        );

        PieChart chart = new PieChart(slice);
        chart.setTitle(titulo);
        chart.setLegendVisible(true);
        chart.setLabelsVisible(false);

        Platform.runLater(() -> {
            for (PieChart.Data d : chart.getData()) {
                d.getNode().setStyle("-fx-cursor: hand");
            }
        });

        return chart;
    }
}
