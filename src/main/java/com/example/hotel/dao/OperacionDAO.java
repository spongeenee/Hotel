package com.example.hotel.dao;

import com.example.hotel.models.Accion;
import com.example.hotel.models.Operacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OperacionDAO extends GenericDAO<Operacion> {
    public OperacionDAO() {
        super();
    }

    @Override
    public int registrar(Operacion operacion) {
        String sql = "INSERT INTO Operacion (id_responsable, id_reservacion, accion, hora, motivo) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, operacion.responsableID());
            stmt.setLong(2, operacion.reservacionID());
            stmt.setString(3, operacion.accion().name());
            stmt.setTimestamp(4, Timestamp.valueOf(operacion.hora()));
            stmt.setString(5, operacion.motivo());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al registrar operación: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int actualizar(Operacion operacion) {
        return 0;
    }

    @Override
    public int eliminar(Long ID) {
        String sql = "DELETE FROM Operacion WHERE id_operacion=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, ID);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar operación: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Optional<Operacion> buscarPorID(Long ID) {
        String sql = "SELECT * FROM Operacion WHERE id_operacion=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, ID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(resultSetAObjeto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar operación por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Operacion> listar() {
        List<Operacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Operacion";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(resultSetAObjeto(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar operaciones: " + e.getMessage());
        }
        return lista;
    }

    @Override
    protected Operacion resultSetAObjeto(ResultSet rs) throws SQLException {
        return new Operacion(
                rs.getLong("id_operacion"),
                rs.getLong("id_responsable"),
                rs.getLong("id_reservacion"),
                Accion.valueOf(rs.getString("accion")),
                rs.getTimestamp("hora").toLocalDateTime(),
                rs.getString("motivo")
        );
    }
}
