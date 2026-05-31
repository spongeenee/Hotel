package com.example.hotel.dao;

import com.example.hotel.singleton.ConexionMySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Clase genérica DAO que proporciona operaciones CRUD básicas.
 * GenericDAO manejado desde prácticas anteriores.
 * @param <T> Tipo de entidad a manejar
 * @author marlon
 */
public abstract class GenericDAO<T> {
    protected final Connection conexion;

    public GenericDAO() {
        this.conexion = ConexionMySQL.getInstancia().getConexion();
    }

    /**
     * Guarda una entidad en la base de datos
     * @param entity Entidad a guardar
     * @return true si la operación fue exitosa
     */
    public abstract int registrar(T entity);

    /**
     * Actualiza una entidad en la base de datos
     * @param entity Entidad a actualizar
     * @return true si la operación fue exitosa
     */
    public abstract int actualizar(T entity);

    /**
     * Elimina una entidad de la base de datos
     * @param ID Identificador de la entidad
     * @return true si la operación fue exitosa
     */
    public abstract int eliminar(Long ID) throws SQLException;

    /**
     * Busca una entidad por su ID
     * @param ID Identificador de la entidad
     * @return La entidad encontrada o null
     */
    public abstract Optional<T> buscarPorID(Long ID) throws SQLException;

    /**
     * Obtiene todas las entidades
     * @return Lista de entidades
     */
    public abstract List<T> listar() throws SQLException;

    /**
     * Convierte un ResultSet en una entidad
     * @param rs ResultSet a convertir
     * @return Entidad creada
     * @throws SQLException Si ocurre un error de SQL
     */
    protected abstract T resultSetAObjeto(ResultSet rs) throws SQLException;
}
