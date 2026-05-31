package com.example.hotel.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    private static ConexionMySQL instancia;
    private Connection conexion;

    private ConexionMySQL() {
        try {
            String url = "jdbc:mysql://localhost:3306/HotelDB";
            String usuario = "root";
            String password = "password";
            this.conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("O Conexión a MySQL establecida correctamente.");
        } catch (SQLException e) {
            throw new RuntimeException("X Error al conectar a MySQL: " + e.getMessage(), e);
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
