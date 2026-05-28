package com.example.hotel.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    private static ConexionMySQL instancia;
    private Connection conexion;

    private ConexionMySQL() {
        try {
            String url = "jdbc:mysql://localhost:3306/hotel?useSSL=false&serverTimezone=UTC";
            String usuario = "root";
            String contraseña = "1234";
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("✅ Conexión a MySQL establecida correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a MySQL: " + e.getMessage());
            conexion = null;
        }
    }

    public static ConexionMySQL getInstancia() {
        if (instancia == null) {
            instancia = new ConexionMySQL();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }
}
