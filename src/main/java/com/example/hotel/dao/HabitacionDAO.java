package com.example.hotel.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.example.hotel.models.Habitacion;
import com.example.hotel.models.TipoHabitacion;
import com.example.hotel.singleton.ConexionMySQL;

public class HabitacionDAO {
    private Connection conexion;

    public HabitacionDAO() {
        conexion = ConexionMySQL.getInstancia().getConexion();
    }

    public void agregarHabitacion(Habitacion habitacion) {
        String sql = "INSERT INTO Habitacion (id_tipo, tarifa_por_dia, ocupada, fuera_de_servicio) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, habitacion.getTipo().ordinal());
            stmt.setDouble(2, habitacion.tarifaPerDia());
            stmt.setBoolean(3, habitacion.estaOcupada());
            stmt.setBoolean(4, habitacion.fueraDeServicio());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar habitación: " + e.getMessage());
        }
    }

    public List<Habitacion> obtenerHabitaciones() {
        List<Habitacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Habitacion";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Habitacion h = Habitacion.conTarifaPersonalizada(
                        rs.getLong("id_habitacion"),
                        TipoHabitacion.values()[rs.getInt("id_tipo")],
                        rs.getDouble("tarifa_por_dia")
                );
                h.definirDisponibilidad(rs.getBoolean("ocupada"));
                h.definirServicio(rs.getBoolean("fuera_de_servicio"));
                lista.add(h);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener habitaciones: " + e.getMessage());
        }
        return lista;
    }
}

