package com.example.hotel.dao;

import com.example.hotel.models.RecepcionistaRol;
import com.example.hotel.models.SupervisorRol;
import com.example.hotel.models.Usuario;
import com.example.hotel.models.UsuarioRol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAO extends GenericDAO<Usuario> {
    public UsuarioDAO() {
        super();
    }

    @Override
    public int registrar(Usuario usuario) {
        String sql = "INSERT INTO Usuario (usuario, password_hash, rol, ultimo_login) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.usuario());
            stmt.setString(2, usuario.passwordHash());
            stmt.setString(3, usuario.rol());
            stmt.setTimestamp(4, Timestamp.valueOf(usuario.ultimoLogin()));
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int actualizar(Usuario usuario) {
        String sql = "UPDATE Usuario SET usuario=?, password_hash=?, rol=?, ultimo_login=? WHERE id_usuario=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.usuario());
            stmt.setString(2, usuario.passwordHash());
            stmt.setString(3, usuario.rol());
            stmt.setTimestamp(4, Timestamp.valueOf(usuario.ultimoLogin()));
            stmt.setLong(5, usuario.ID());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int eliminar(long ID) {
        String sql = "DELETE FROM Usuario WHERE id_usuario=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, ID);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Optional<Usuario> buscarPorID(Long ID) {
        String sql = "SELECT * FROM Usuario WHERE id_usuario=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, ID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(resultSetAObjeto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(resultSetAObjeto(rs));
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    @Override
    protected Usuario resultSetAObjeto(ResultSet rs) throws SQLException {
        return new Usuario.Builder()
                .ID(rs.getLong("id_usuario"))
                .usuario(rs.getString("usuario"))
                .passwordHash(rs.getString("password_hash"))
                .rol(rolFromString(rs.getString("rol")))
                .build();
    }

    private UsuarioRol rolFromString(String rol) {
        if ("SUPERVISOR".equals(rol)) return new SupervisorRol();
        return new RecepcionistaRol();
    }
}

/*
 * ---- Métodos legacy (código anterior) ----
 *
 * public void registrarUsuario(Usuario usuario) {
 *     String sql = "INSERT INTO Usuario (usuario, password_hash, id_rol, ultimo_login) VALUES (?, ?, ?, ?)";
 *     try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
 *         stmt.setString(1, usuario.usuario());
 *         stmt.setString(2, usuario.passwordHash());
 *         stmt.setString(3, usuario.rol());
 *         stmt.setTimestamp(4, Timestamp.valueOf(usuario.ultimoLogin()));
 *         stmt.executeUpdate();
 *     } catch (SQLException e) {
 *         System.err.println("Error al registrar usuario: " + e.getMessage());
 *     }
 * }
 *
 * public Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
 *     String sql = "SELECT * FROM Usuario WHERE usuario = ?";
 *     try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
 *         stmt.setString(1, nombreUsuario);
 *         ResultSet rs = stmt.executeQuery();
 *         if (rs.next()) {
 *             return new Usuario.Builder()
 *                     .ID(rs.getLong("id_usuario"))
 *                     .usuario(rs.getString("usuario"))
 *                     .passwordHash(rs.getString("password_hash"))
 *                     .rol(
 *                             (rs.getString("id_rol").equals("SUPERVISOR") ? new SupervisorRol()
 *                             : new RecepcionistaRol()))
 *                     .self()
 *                     .build();
 *         }
 *     } catch (SQLException e) {
 *         System.err.println("Error al obtener usuario: " + e.getMessage());
 *     }
 *     return null;
 * }
 */
