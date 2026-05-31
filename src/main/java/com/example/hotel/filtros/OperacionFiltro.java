package com.example.hotel.filtros;

import com.example.hotel.models.Accion;
import com.example.hotel.models.Operacion;
import com.example.hotel.models.Reservacion;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OperacionFiltro {
    private final long responsableID;
    private final long reservacionID;
    private final Accion accion;
    private final LocalDate desde;
    private final LocalDate hasta;

    public OperacionFiltro(long reservacionID, Accion accion, LocalDate desde, LocalDate hasta) {
        this.responsableID = reservacionID;
        this.reservacionID = reservacionID;
        this.accion = accion;
        this.desde = desde;
        this.hasta = hasta;
    }

    public long responsableID() {
        return responsableID;
    }

    public long reservacionID() {
        return reservacionID;
    }

    public Accion accion() {
        return accion;
    }

    public LocalDate desde() {
        return desde;
    }

    public LocalDate hasta() {
        return hasta;
    }
}
