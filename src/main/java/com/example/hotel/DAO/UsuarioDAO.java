package com.example.hotel.DAO;

import java.sql.*;
import com.example.hotel.models.Usuario;
import com.example.hotel.Conexion.ConexionMySQL;

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
            stmt.setInt(3, usuario.rol().ID());
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
                        .rol(new UsuarioRol(rs.getInt("id_rol"), ""))
                        .self()
                        .build();
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
        }
        return null;
    }
}
