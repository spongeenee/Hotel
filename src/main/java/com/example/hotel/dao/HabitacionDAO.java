package com.example.hotel.dao;

import com.example.hotel.models.Habitacion;
import com.example.hotel.models.HabitacionEstado;
import com.example.hotel.models.TipoHabitacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HabitacionDAO extends GenericDAO<Habitacion> {
    public HabitacionDAO() throws SQLException {
        super();
    }

    @Override
    public int registrar(Habitacion habitacion) {
        String sql = "INSERT INTO Habitacion (tipo, tarifa_por_dia, estado) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, habitacion.tipo().name());
            stmt.setDouble(2, habitacion.tarifaPerDia());
            stmt.setString(3, habitacion.estado().name());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) return 0;
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) habitacion.setID(keys.getLong(1));
                else return 0;
            }
            return 1;
        } catch (SQLException e) {
            System.err.println("Error al registrar habitación: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int actualizar(Habitacion habitacion) {
        String sql = "UPDATE Habitacion SET tipo=?, tarifa_por_dia=?, estado=? WHERE id_habitacion=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, habitacion.tipo().name());
            stmt.setDouble(2, habitacion.tarifaPerDia());
            stmt.setString(3, habitacion.estado().name());
            stmt.setLong(4, habitacion.ID());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar habitación: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int eliminar(Long ID) {
        String sql = "DELETE FROM Habitacion WHERE id_habitacion=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, ID);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar habitación: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Optional<Habitacion> buscarPorID(Long ID) {
        String sql = "SELECT * FROM Habitacion WHERE id_habitacion=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, ID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(resultSetAObjeto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar habitación por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Habitacion> listar() {
        List<Habitacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Habitacion";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(resultSetAObjeto(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar habitaciones: " + e.getMessage());
        }
        return lista;
    }

    @Override
    protected Habitacion resultSetAObjeto(ResultSet rs) throws SQLException {
        Habitacion h = Habitacion.conTarifaPersonalizada(
                rs.getLong("id_habitacion"),
                TipoHabitacion.valueOf(rs.getString("tipo")),
                rs.getDouble("tarifa_por_dia")
        );
        h.cambiarEstado(HabitacionEstado.valueOf(rs.getString("estado")));
        return h;
    }
}

/*
 * ---- Métodos legacy (código anterior) ----
 *
 * public int registrar(Habitacion habitacion) {
 *     String sql = "INSERT INTO Habitacion (id_tipo, tarifa_por_dia, ocupada, fuera_de_servicio) VALUES (?, ?, ?, ?)";
 *     try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
 *         stmt.setString(1, habitacion.tipo().toString());
 *         stmt.setDouble(2, habitacion.tarifaPerDia());
 *         stmt.setString(3, habitacion.estado().toString());
 *         int affectedRows = stmt.executeUpdate();
 *         if (affectedRows == 0) return 0;
 *         try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
 *             if (generatedKeys.next()) {
 *                 habitacion.setID(generatedKeys.getLong(1));
 *             } else {
 *                 return 0;
 *             }
 *         }
 *         return 1;
 *     } catch (SQLException e) {
 *         System.err.println("Error al agregar habitación: " + e.getMessage());
 *     }
 *     return 0;
 * }
 *
 * public List<Habitacion> listar() {
 *     List<Habitacion> lista = new ArrayList<>();
 *     String sql = "SELECT * FROM Habitacion";
 *     try (Statement stmt = conexion.createStatement();
 *          ResultSet rs = stmt.executeQuery(sql)) {
 *         while (rs.next()) {
 *             Habitacion h = Habitacion.conTarifaPersonalizada(
 *                     rs.getLong("id_habitacion"),
 *                     TipoHabitacion.values()[rs.getInt("id_tipo")],
 *                     rs.getDouble("tarifa_por_dia")
 *             );
 *             lista.add(h);
 *         }
 *     } catch (SQLException e) {
 *         System.err.println("Error al obtener habitaciones: " + e.getMessage());
 *     }
 *     return lista;
 * }
 */
