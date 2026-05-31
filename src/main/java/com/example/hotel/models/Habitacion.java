package com.example.hotel.models;

public class Habitacion {
    private long ID;
    private final TipoHabitacion tipo;
    private final double tarifaPerDia;
    private HabitacionEstado estado;

    private Habitacion(long ID,  TipoHabitacion tipo, double tarifaPerDia) {
        this.ID = ID;
        this.tipo = tipo;
        this.tarifaPerDia = tarifaPerDia;
        this.estado = HabitacionEstado.DISPONIBLE;
    }
    public TipoHabitacion tipo() {
        return tipo;
    }

    public static Habitacion deTipo(long ID, TipoHabitacion tipo) {
        return new Habitacion(ID, tipo, tipo.ordinal());
    }

    public static Habitacion conTarifaPersonalizada(long ID, TipoHabitacion tipo, double tarifaPerDia) {
        return new Habitacion(ID, tipo, tarifaPerDia);
    }

    public long ID() {
        return ID;
    }

    public void setID (long ID) {
        this.ID = ID;
    }

    public double tarifaPerDia() {
        return tarifaPerDia;
    }

    public double calcularTarifa(int dias) {
        return tarifaPerDia * dias;
    }

    public HabitacionEstado estado() {
        return estado;
    }

    public void cambiarEstado(HabitacionEstado estado) {
        this.estado = estado;
    }

    public boolean disponible() {
        return estado == HabitacionEstado.DISPONIBLE;
    }

    public boolean ocupado() {
        return estado == HabitacionEstado.OCUPADO;
    }

    public boolean fueraDeServicio() {
        return estado == HabitacionEstado.FUERA_DE_SERVICIO;
    }

    @Override
    public String toString() {
        return "Habicación " + ID + " - " + tipo + " - " + tarifaPerDia;
    }
}
