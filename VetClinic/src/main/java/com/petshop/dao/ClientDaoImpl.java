package com.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.petshop.model.Clients;
import com.petshop.utils.ConexionPOSTGRES;

public class ClientDaoImpl implements BaseDAO<Clients>{
    private Connection conexion;

    public ClientDaoImpl() {
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
    public void insertar(Clients client) {

        String sql = "INSERT INTO client (NAME, LAST_NAME, EMAIL, PHONE, IDENTIFICATION, ADDRESS) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)){
            statement.setString(1 , client.getName());
            statement.setString(2 , client.getLastname());
            statement.setString(3 , client.getEmail());
            statement.setString(4 , client.getPhone());
            statement.setString(5 , client.getIdentification());
            statement.setString(6 , client.getAddress());

            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Clients buscarPorId(int id) {
        String sql = "SELECT * FROM client WHERE CLIENT_ID = ?";
        try(PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Clients client = new Clients(null, null, null, null, null, null);
                    client.setIdClient(resultSet.getInt("CLIENT_ID"));
                    client.setName(resultSet.getString("NAME"));
                    client.setLastname(resultSet.getString("LAST_NAME"));
                    client.setEmail(resultSet.getString("EMAIL"));
                    client.setPhone(resultSet.getString("PHONE"));
                    client.setIdentification(resultSet.getString("IDENTIFICATION"));
                    client.setAddress(resultSet.getString("ADDRESS"));
                    
                    return client;
                } 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Clients> buscarTodos() {
        List<Clients> clientsOfDb = new ArrayList<>();
        String sql = "SELECT * FROM client";
        try(PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Clients client = new Clients(null, null, null, null, null, null);
                client.setIdClient(resultSet.getInt("CLIENT_ID"));
                client.setName(resultSet.getString("NAME"));
                client.setLastname(resultSet.getString("LAST_NAME"));
                client.setEmail(resultSet.getString("EMAIL"));
                client.setPhone(resultSet.getString("PHONE"));
                client.setIdentification(resultSet.getString("IDENTIFICATION"));
                client.setAddress(resultSet.getString("ADDRESS"));
                
                clientsOfDb.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientsOfDb;
    }

    @Override
    public void actualizar(Clients client) {
        String sql = "UPDATE client SET NAME = ?, LAST_NAME = ?, EMAIL = ?, PHONE = ?, IDENTIFICATION = ?, ADDRESS = ? WHERE CLIENT_ID = ?";
        try(PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getLastname());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getPhone());
            statement.setString(5, client.getIdentification());
            statement.setString(6, client.getAddress());
            statement.setInt(7, client.getIdClient());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();    
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM client WHERE CLIENT_ID = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
