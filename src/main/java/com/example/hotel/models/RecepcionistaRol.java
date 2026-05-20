package com.example.hotel.models;

public class RecepcionistaRol extends UsuarioRol {
    public RecepcionistaRol() {
        super();
        permisos.add(Permiso.CREAR_RESERVACION);
        permisos.add(Permiso.VER_RESERVACIONES);
        permisos.add(Permiso.MODIFICAR_RESERVACION);
    }
}
