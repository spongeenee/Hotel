package com.example.hotel.dao;

import java.sql.*;

import com.example.hotel.models.RecepcionistaRol;
import com.example.hotel.models.SupervisorRol;
import com.example.hotel.models.Usuario;
import com.example.hotel.singleton.ConexionMySQL;

public class UsuarioDAO {
    private Connection conexion;

    public UsuarioDAO() {
        conexion = ConexionMySQL.getInstancia().getConexion();
    }

    public void registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuario (usuario, password_hash, id_rol, ultimo_login) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.usuario());
            stmt.setString(2, usuario.passwordHash());
            stmt.setString(3, usuario.rol());
            stmt.setTimestamp(4, Timestamp.valueOf(usuario.ultimoLogin()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
        }
    }

    public Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
        String sql = "SELECT * FROM Usuario WHERE usuario = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario.Builder()
                        .ID(rs.getLong("id_usuario"))
                        .usuario(rs.getString("usuario"))
                        .passwordHash(rs.getString("password_hash"))
                        .rol(
                                (rs.getString("id_rol").equals("SUPERVISOR") ? new SupervisorRol()
                                : new RecepcionistaRol()))
                        .self()
                        .build();
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
        }
        return null;
    }
}
