package com.example.hotel.models;

public class Habitacion {
    private final long ID;
    private final TipoHabitacion tipo;
    private final double tarifaPerDia;
    private boolean ocupada;
    private boolean fueraDeServicio;

    private Habitacion(long ID,  TipoHabitacion tipo, double tarifaPerDia) {
        this.ID = ID;
        this.tipo = tipo;
        this.tarifaPerDia = tarifaPerDia;
        this.ocupada = false;
        this.fueraDeServicio = false;
    }

    public static Habitacion deTipo(long ID, TipoHabitacion tipo) {
        return new Habitacion(ID, tipo, tipo.ordinal());
    }

    public static Habitacion conTarifaPersonalizada(long ID, TipoHabitacion tipo, double tarifaPerDia) {
        return new Habitacion(ID, tipo, tarifaPerDia);
    }

    public boolean fueraDeServicio() {
        return fueraDeServicio;
    }

    public boolean estaOcupada() {
        return ocupada;
    }

    public double tarifaPerDia() {
        return tarifaPerDia;
    }

    public double calcularTarifa(int dias) {
        return tarifaPerDia * dias;
    }

    public void definirServicio(boolean condicion) {
        this.fueraDeServicio = condicion;
    }

    public void definirDisponibilidad(boolean disponibilidad) {
        this.ocupada = disponibilidad;
    }
}
