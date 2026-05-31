package com.example.hotel.dao;

import com.example.hotel.models.Huesped;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HuespedDAO extends GenericDAO<Huesped> {
    public HuespedDAO() {
        super();
    }

    @Override
    public int registrar(Huesped huesped) {
        String sql = "INSERT INTO Huesped (nombre, mail) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, huesped.nombre());
            stmt.setString(2, huesped.mail());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al registrar huésped: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int actualizar(Huesped huesped) {
        String sql = "UPDATE Huesped SET nombre=?, mail=? WHERE id_huesped=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, huesped.nombre());
            stmt.setString(2, huesped.mail());
            stmt.setLong(3, huesped.ID());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar huésped: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int eliminar(long ID) {
        String sql = "DELETE FROM Huesped WHERE id_huesped=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, ID);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar huésped: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Optional<Huesped> buscarPorID(Long ID) {
        String sql = "SELECT * FROM Huesped WHERE id_huesped=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, ID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(resultSetAObjeto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar huésped por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Huesped> listar() {
        List<Huesped> lista = new ArrayList<>();
        String sql = "SELECT * FROM Huesped";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(resultSetAObjeto(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar huéspedes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    protected Huesped resultSetAObjeto(ResultSet rs) throws SQLException {
        return new Huesped(
                rs.getLong("id_huesped"),
                rs.getString("nombre"),
                rs.getString("mail")
        );
    }
}
