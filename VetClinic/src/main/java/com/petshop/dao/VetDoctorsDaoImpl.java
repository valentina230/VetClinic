package com.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.petshop.model.VetDoctors;
import com.petshop.model.VetSpecialization;
import com.petshop.model.Working;
import com.petshop.utils.ConexionPOSTGRES;

public class VetDoctorsDaoImpl implements BaseDAO<VetDoctors>{
    private Connection conexion;

    public VetDoctorsDaoImpl() {
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
    public VetDoctors buscarPorId(int id) {
        String sql = "SELECT * FROM  VET_DOCTOR WHERE VET_DOCTOR_ID = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)){
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    VetDoctors vet = new VetDoctors();
                    vet.setIdVetDoctor(resultSet.getInt("VET_DOCTOR_ID"));
                    vet.setName(resultSet.getString("NAME"));
                    vet.setLastname(resultSet.getString("LAST_NAME"));
                    vet.setEmail(resultSet.getString("EMAIL"));
                    vet.setPhone(resultSet.getString("PHONE"));
                    vet.setIdentification(resultSet.getString("IDENTIFICATION"));
                    vet.setAddress(resultSet.getString("ADDRESS"));
                    vet.setSpecialization(VetSpecialization.valueOf(resultSet.getString("SPECIALITATION").replace(" ", "_").toUpperCase()));
                    vet.setStatus(Working.valueOf(resultSet.getString("STATUS").toUpperCase()));
                    
                    return vet;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<VetDoctors> buscarTodos() {
        List<VetDoctors> vetOfDb = new ArrayList<>();
        String sql = "SELECT * FROM VET_DOCTOR";
        try(PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                VetDoctors vet = new VetDoctors(null, null, null, null, null, null, null, null);
                vet.setIdVetDoctor(resultSet.getInt("VET_DOCTOR_ID"));
                vet.setName(resultSet.getString("NAME"));
                vet.setLastname(resultSet.getString("LAST_NAME"));
                vet.setEmail(resultSet.getString("EMAIL"));
                vet.setPhone(resultSet.getString("PHONE"));
                vet.setIdentification(resultSet.getString("IDENTIFICATION"));
                vet.setAddress(resultSet.getString("ADDRESS"));
                vet.setSpecialization(VetSpecialization.valueOf(resultSet.getString("SPECIALITATION").replace(" ", "_").toUpperCase()));
                vet.setStatus(Working.valueOf(resultSet.getString("STATUS")));
                
                
                vetOfDb.add(vet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vetOfDb;
    }



    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM vet_Doctor WHERE VET_DOCTOR_ID = ?";
        try(PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    

    @Override
    public void insertar(VetDoctors obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertar'");
    }

    @Override
    public void actualizar(VetDoctors obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }
    
}
