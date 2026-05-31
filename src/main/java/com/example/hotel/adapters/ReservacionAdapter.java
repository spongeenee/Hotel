package com.example.hotel.adapters;

import com.example.hotel.models.Huesped;
import com.example.hotel.models.Reservacion;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import java.time.LocalDateTime;

public class ReservacionAdapter {
    private final Reservacion reservacion;
    private final ObjectProperty<Long> ID;
    private final StringProperty huespedNombre;
    private final StringProperty huespedMail;
    private final ObjectProperty<Long> habitacionID;
    private final StringProperty estado;
    private final ObjectProperty<LocalDateTime> fechaRegistro;
    private final ObjectProperty<LocalDateTime> fechaIngreso;
    private final ObjectProperty<LocalDateTime> fechaSalida;

    public ReservacionAdapter(Reservacion reservacion, Huesped huesped) {
        this.reservacion = reservacion;
        this.ID = new SimpleObjectProperty<>(reservacion.ID());
        this.huespedNombre = new SimpleStringProperty(huesped.nombre());
        this.huespedMail = new SimpleStringProperty(huesped.mail());
        this.habitacionID = new SimpleObjectProperty<>(reservacion.habitacionID());
        this.estado = new SimpleStringProperty(reservacion.estado().toString());
        this.fechaRegistro = new SimpleObjectProperty<>(reservacion.fechaRegistro());
        this.fechaIngreso = new SimpleObjectProperty<>(reservacion.fechaIngreso());
        this.fechaSalida = new SimpleObjectProperty<>(reservacion.fechaSalida());
    }

    public ObjectProperty<Long> IDProperty() {
        return ID;
    }

    public StringProperty huespedNombreProperty() {
        return huespedNombre;
    }

    public StringProperty huespedMailProperty() {
        return huespedMail;
    }

    public ObjectProperty<Long> habitacionIDProperty() {
        return habitacionID;
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public ObjectProperty<LocalDateTime> fechaRegistroProperty() {
        return fechaRegistro;
    }

    public ObjectProperty<LocalDateTime> fechaIngresoProperty() {
        return fechaIngreso;
    }

    public ObjectProperty<LocalDateTime> fechaSalidaProperty() {
        return fechaSalida;
    }

    public Reservacion reservacion() {
        return reservacion;
    }

}
