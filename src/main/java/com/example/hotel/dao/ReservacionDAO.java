package com.example.hotel.dao;

import com.example.hotel.models.Estado;
import com.example.hotel.models.Reservacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservacionDAO extends GenericDAO<Reservacion> {
    public ReservacionDAO() {
        super();
    }

    @Override
    public int registrar(Reservacion reservacion) {
        String sql = "INSERT INTO Reservacion (id_huesped, id_habitacion, estado, fecha_registro, fecha_ingreso, fecha_salida) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, reservacion.huespedID());
            stmt.setLong(2, reservacion.habitacionID());
            stmt.setString(3, reservacion.estado().name());
            stmt.setTimestamp(4, Timestamp.valueOf(reservacion.fechaRegistro()));
            stmt.setTimestamp(5, Timestamp.valueOf(reservacion.fechaIngreso()));
            stmt.setTimestamp(6, Timestamp.valueOf(reservacion.fechaSalida()));
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al registrar reservación: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int actualizar(Reservacion reservacion) {
        String sql = "UPDATE Reservacion SET id_huesped=?, id_habitacion=?, estado=?, fecha_registro=?, fecha_ingreso=?, fecha_salida=? WHERE id_reservacion=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, reservacion.huespedID());
            stmt.setLong(2, reservacion.habitacionID());
            stmt.setString(3, reservacion.estado().name());
            stmt.setTimestamp(4, Timestamp.valueOf(reservacion.fechaRegistro()));
            stmt.setTimestamp(5, Timestamp.valueOf(reservacion.fechaIngreso()));
            stmt.setTimestamp(6, Timestamp.valueOf(reservacion.fechaSalida()));
            stmt.setLong(7, reservacion.ID());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar reservación: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int eliminar(long ID) {
        String sql = "DELETE FROM Reservacion WHERE id_reservacion=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, ID);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar reservación: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Optional<Reservacion> buscarPorID(Long ID) {
        String sql = "SELECT * FROM Reservacion WHERE id_reservacion=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, ID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(resultSetAObjeto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar reservación por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Reservacion> listar() {
        List<Reservacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Reservacion";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(resultSetAObjeto(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar reservaciones: " + e.getMessage());
        }
        return lista;
    }

    @Override
    protected Reservacion resultSetAObjeto(ResultSet rs) throws SQLException {
        return new Reservacion.Builder()
                .ID(rs.getLong("id_reservacion"))
                .huespedID(rs.getLong("id_huesped"))
                .habitacionID(rs.getLong("id_habitacion"))
                .estado(Estado.valueOf(rs.getString("estado")))
                .fechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime())
                .fechaIngreso(rs.getTimestamp("fecha_ingreso").toLocalDateTime())
                .fechaSalida(rs.getTimestamp("fecha_salida").toLocalDateTime())
                .self()
                .build();
    }
}

/*
 * ---- Métodos legacy (código anterior) ----
 *
 * public void agregarReservacion(Reservacion reservacion) {
 *     String sql = "INSERT INTO Reservacion (id_huesped, id_habitacion, id_estado, fecha_registro, fecha_ingreso, fecha_salida) VALUES (?, ?, ?, ?, ?, ?)";
 *     try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
 *         stmt.setLong(1, reservacion.huespedID());
 *         stmt.setLong(2, reservacion.habitacionID());
 *         stmt.setString(3, reservacion.estado().toString());
 *         stmt.setTimestamp(4, Timestamp.valueOf(reservacion.fechaRegistro()));
 *         stmt.setTimestamp(5, Timestamp.valueOf(reservacion.fechaIngreso()));
 *         stmt.setTimestamp(6, Timestamp.valueOf(reservacion.fechaSalida()));
 *         stmt.executeUpdate();
 *     } catch (SQLException e) {
 *         System.err.println("Error al agregar reservación: " + e.getMessage());
 *     }
 * }
 *
 * public List<Reservacion> obtenerReservaciones() {
 *     List<Reservacion> lista = new ArrayList<>();
 *     String sql = "SELECT * FROM Reservacion";
 *     try (Statement stmt = conexion.createStatement();
 *          ResultSet rs = stmt.executeQuery(sql)) {
 *         while (rs.next()) {
 *             Reservacion r = new Reservacion.Builder()
 *                     .ID(rs.getLong("id_reservacion"))
 *                     .huespedID(rs.getLong("id_huesped"))
 *                     .habitacionID(rs.getLong("id_habitacion"))
 *                     .fechaIngreso(rs.getTimestamp("fecha_ingreso").toLocalDateTime().toLocalDate())
 *                     .fechaSalida(rs.getTimestamp("fecha_salida").toLocalDateTime().toLocalDate())
 *                     .self()
 *                     .build();
 *             lista.add(r);
 *         }
 *     } catch (SQLException e) {
 *         System.err.println("Error al obtener reservaciones: " + e.getMessage());
 *     }
 *     return lista;
 * }
 *
 * public void actualizarEstadoReservacion(long idReservacion, int nuevoEstadoId) {
 *     String sql = "UPDATE Reservacion SET id_estado = ? WHERE id_reservacion = ?";
 *     try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
 *         stmt.setInt(1, nuevoEstadoId);
 *         stmt.setLong(2, idReservacion);
 *         stmt.executeUpdate();
 *     } catch (SQLException e) {
 *         System.err.println("Error al actualizar estado: " + e.getMessage());
 *     }
 * }
 */
