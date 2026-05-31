package com.example.hotel.service;

import com.example.hotel.dao.OperacionDAO;
import com.example.hotel.filtros.OperacionFiltro;
import com.example.hotel.models.Accion;
import com.example.hotel.models.Operacion;
import com.example.hotel.models.Reservacion;
import com.example.hotel.models.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OperacionService extends GenericService<Operacion> {
    public OperacionService() {
        this.dao = new OperacionDAO();
    }

    public int registrar(Operacion operacion) {
        if (operacion != null)
            return dao.registrar(operacion);
        else return 0;
    }

    public int actualizar(Operacion operacion) {
        if (operacion != null)
            return dao.actualizar(operacion);
        else return 0;
    }

    public int eliminar(Operacion operacion) {
        if (operacion != null)
            return dao.actualizar(operacion);
        else return 0;
    }

    public List<Operacion> listar() {
        return dao.listar();
    }

    public List<Operacion> filtrarPorResponsable(long responsableID) {
        List<Operacion> filtrado = new ArrayList<>();
        for (Operacion operacion : listar()) {
            if (operacion.responsableID() == responsableID)
                filtrado.add(operacion);
        }
        return filtrado;
    }

    public List<Operacion> filtrarEntreFechas(LocalDate desde, LocalDate hasta) {
        List<Operacion> filtrado = new ArrayList<>();
        for (Operacion operacion : listar()) {
            if (operacion.hora().toLocalDate().isBefore(hasta)
                    && operacion.hora().toLocalDate().isAfter(desde))
                filtrado.add(operacion);
        }
        return filtrado;
    }

    public List<Operacion> filtrarPorAccion(Accion accion) {
        List<Operacion> filtrado = new ArrayList<>();
        for (Operacion operacion : listar()) {
            if (operacion.accion().equals(accion))
                filtrado.add(operacion);
        }
        return filtrado;
    }

    public List<Operacion> filtrarPorFiltro(OperacionFiltro filtro) {
        List<Operacion> filtrado = new ArrayList<>();
        for (Operacion operacion : listar()) {
            if (filtro.responsableID() >= 0)
                filtrado.addAll(filtrarPorResponsable(filtro.responsableID()));
            if (filtro.desde() != null && filtro.hasta() != null )
                filtrado.addAll(filtrarEntreFechas(filtro.desde(), filtro.hasta()));
            if (filtro.accion() != null)
                filtrado.addAll(filtrarPorAccion(filtro.accion()));
        }
        return filtrado;
    }
}
