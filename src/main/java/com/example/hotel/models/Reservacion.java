package com.example.hotel.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservacion {
    private final long ID;
    private final long huespedID;
    private final long habitacionID;
    private final Estado estado;
    private final LocalDateTime fechaRegistro;
    private final LocalDateTime fechaIngreso;
    private final LocalDateTime fechaSalida;

    public Reservacion(Builder builder) {
        this.ID = builder.ID;
        this.huespedID = builder.huespedID;
        this.habitacionID = builder.habitacionID;
        this.estado = builder.estado;
        this.fechaRegistro = builder.fechaRegistro;
        this.fechaIngreso = builder.fechaIngreso;
        this.fechaSalida = builder.fechaSalida;
    }

    public long ID() {
        return ID;
    }
    public long huespedID() {
        return huespedID;
    }

    public long habitacionID() {
        return habitacionID;
    }

    public Estado estado() {
        return estado;
    }

    public LocalDateTime fechaRegistro() {
        return fechaRegistro;
    }

    public LocalDateTime fechaIngreso() {
        return fechaIngreso;
    }

    public LocalDateTime fechaSalida() {
        return fechaSalida;
    }

    public static class Builder {
        private long ID;
        private long huespedID;
        private long habitacionID;
        private Estado estado;
        private LocalDateTime fechaRegistro;
        private LocalDateTime fechaIngreso;
        private LocalDateTime fechaSalida;

        public Builder() {
            fechaRegistro = LocalDateTime.now();
        }

        public Reservacion.Builder ID(long ID) {
            this.ID = ID;
            return this;
        }

        public Reservacion.Builder huespedID(long huespedID) {
            this.huespedID = huespedID;
            return this;
        }

        public Reservacion.Builder habitacionID(long habitacionID) {
            this.habitacionID = habitacionID;
            return this;
        }

        public Reservacion.Builder estado(Estado estado) {
            this.estado = estado;
            return this;
        }

        public Reservacion.Builder fechaRegistro(LocalDateTime fechaRegistro) {
            this.fechaRegistro = fechaRegistro;
            return this;
        }

        public Reservacion.Builder fechaIngreso(LocalDate fechaIngreso) {
            this.fechaIngreso = fechaIngreso.atTime(LocalTime.NOON);
            return this;
        }

        public Reservacion.Builder fechaIngreso(LocalDateTime fechaIngreso) {
            this.fechaIngreso = fechaIngreso;
            return this;
        }

        public Reservacion.Builder fechaSalida(LocalDate fechaSalida) {
            this.fechaSalida = fechaSalida.atTime(LocalTime.NOON);
            return this;
        }

        public Reservacion.Builder fechaSalida(LocalDateTime fechaSalida) {
            this.fechaSalida = fechaSalida;
            return this;
        }

        public Reservacion.Builder self() {
            return this;
        }

        public Reservacion build() {
            return new Reservacion(this);
        }
    }
}