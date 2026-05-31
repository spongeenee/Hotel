package com.example.hotel.filtros;

import com.example.hotel.models.Estado;

import java.time.LocalDateTime;

public class ReservacionFiltro {
    private final Estado estado;
    private final LocalDateTime desde;
    private final LocalDateTime hasta;
    private final String mail;

    public ReservacionFiltro(Estado estado, LocalDateTime desde, LocalDateTime hasta, String mail) {
        this.estado = estado;
        this.desde = desde;
        this.hasta = hasta;
        this.mail = mail;
    }

    public Estado estado() {
        return estado;
    }

    public LocalDateTime desde() {
        return desde;
    }

    public LocalDateTime hasta() {
        return hasta;
    }

    public String mail() {
        return mail;
    }
}
