package com.example.hotel.models;

public class SupervisorRol extends UsuarioRol {
    public SupervisorRol() {
        super();
        permisos.add(Permiso.VER_RESERVACIONES);
        permisos.add(Permiso.VER_RESERVACIONES_CANCELADAS);
        permisos.add(Permiso.MODIFICAR_RESERVACION);
        permisos.add(Permiso.CANCELAR_RESERVACION);
        permisos.add(Permiso.GESTIONAR_HABITACIONES);
        permisos.add(Permiso.GESTIONAR_CATALOGOS);
        permisos.add(Permiso.VER_AUDITORIA);
    }
}
