package com.example.hotel.service;

import com.example.hotel.adapters.ReservacionAdapter;
import com.example.hotel.dao.ReservacionDAO;
import com.example.hotel.models.Huesped;
import com.example.hotel.models.Reservacion;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservacionService extends GenericService<Reservacion> {
    public ReservacionService() {
        try {
            this.dao = new ReservacionDAO();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public int registrar(Reservacion reservacion) {
        if (reservacion != null)
            return dao.registrar(reservacion);
        else return 0;
    }

    public int actualizar(Reservacion reservacion) {
        if (reservacion != null)
            return dao.actualizar(reservacion);
        else return 0;
    }

    public int eliminar(Reservacion reservacion) {
        if (reservacion != null)
            return dao.eliminar(reservacion.ID());
        else return 0;
    }

    public List<Reservacion> listar() {
        return dao.listar();
    }

    public List<Reservacion> filtrarPorFecha(LocalDate desde, LocalDate hasta) {
        List<Reservacion> filtrado = new ArrayList<>();
        for (Reservacion reservacion : listar()) {
            if (reservacion.fechaIngreso().toLocalDate().isAfter(desde)
                    && reservacion.fechaSalida().toLocalDate().isBefore(hasta))
                filtrado.add(reservacion);
            else if (reservacion.fechaIngreso().toLocalDate().equals(desde)
                    || reservacion.fechaSalida().toLocalDate().equals(hasta))
                filtrado.add(reservacion);
        }
        return filtrado;
    }

    public List<Reservacion> filtrarPorHuesped(Huesped huesped) {
        List<Reservacion> filtrado = new ArrayList<>();
        for (Reservacion reservacion : listar()) {
            if (reservacion.huespedID() == huesped.ID())
                filtrado.add(reservacion);
        }
        return filtrado;
    }

    public List<ReservacionAdapter> reservacionAAdapter(List<Reservacion> reservaciones) {
        List<ReservacionAdapter> reservacionAdapters = new ArrayList<>();
        for (Reservacion reservacion : reservaciones) {
            reservacionAdapters.add(new ReservacionAdapter(reservacion, null));
        }
        return reservacionAdapters;
    }
}
