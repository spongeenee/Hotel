package com.example.hotel.reports;

import com.example.hotel.models.Reservacion;

import java.util.List;

public interface ReporteStrategy {
    void generarReporte(List<Reservacion> lista, String destino) throws Exception;
}
