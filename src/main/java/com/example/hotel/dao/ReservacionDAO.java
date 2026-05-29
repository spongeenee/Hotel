package com.example.hotel.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.example.hotel.models.Reservacion;
import com.example.hotel.singleton.ConexionMySQL;

public class ReservacionDAO {
    private Connection conexion;

    public ReservacionDAO() {
        conexion = ConexionMySQL.getInstancia().getConexion();
    }

    public void agregarReservacion(Reservacion reservacion) {
        String sql = "INSERT INTO Reservacion (id_huesped, id_habitacion, id_estado, fecha_registro, fecha_ingreso, fecha_salida) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, reservacion.huespedID());
            stmt.setLong(2, reservacion.habitacionID());
            stmt.setString(3, reservacion.estado().toString());
            stmt.setTimestamp(4, Timestamp.valueOf(reservacion.fechaRegistro()));
            stmt.setTimestamp(5, Timestamp.valueOf(reservacion.fechaIngreso()));
            stmt.setTimestamp(6, Timestamp.valueOf(reservacion.fechaSalida()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar reservación: " + e.getMessage());
        }
    }

    public List<Reservacion> obtenerReservaciones() {
        List<Reservacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Reservacion";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Reservacion r = new Reservacion.Builder()
                        .ID(rs.getLong("id_reservacion"))
                        .huespedID(rs.getLong("id_huesped"))
                        .habitacionID(rs.getLong("id_habitacion"))
                        .fechaIngreso(rs.getTimestamp("fecha_ingreso").toLocalDateTime().toLocalDate())
                        .fechaSalida(rs.getTimestamp("fecha_salida").toLocalDateTime().toLocalDate())
                        .self()
                        .build();
                lista.add(r);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener reservaciones: " + e.getMessage());
        }
        return lista;
    }

    public void actualizarEstadoReservacion(long idReservacion, int nuevoEstadoId) {
        String sql = "UPDATE Reservacion SET id_estado = ? WHERE id_reservacion = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, nuevoEstadoId);
            stmt.setLong(2, idReservacion);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado: " + e.getMessage());
        }
    }
}
