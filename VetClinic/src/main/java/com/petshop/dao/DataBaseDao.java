package com.petshop.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.petshop.utils.ConexionPOSTGRES;

public class DataBaseDao implements BaseDAO {
    private Connection conexion;

    public DataBaseDao() {
        try {
            //this.conexion = ConexionMySQL.obtenerConexion();
            this.conexion = ConexionPOSTGRES.obtenerConexion();
        } catch (SQLException e) {
            System.err.println("Error al obtener la conexión: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("No se pudo obtener la conexión a la base de datos", e);
        }
    }
    
    @Override
    public void insertar(Object obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertar'");
    }

    @Override
    public Object buscarPorId(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }

    @Override
    public List buscarTodos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarTodos'");
    }

    @Override
    public void actualizar(Object obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public void eliminar(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    
    
}
