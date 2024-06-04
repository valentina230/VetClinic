package com.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.petshop.model.HistoriesPet;
import com.petshop.utils.ConexionPOSTGRES;

public class HistoriesPetDaoImpl implements BaseDAO<HistoriesPet>{
    private Connection conexion;
    // From Dao get the info
    private ClientDaoImpl clientsDao = new ClientDaoImpl(); 
    private VetDoctorsDaoImpl vetDao = new VetDoctorsDaoImpl();
    private PetDaoImpl petDao = new PetDaoImpl();

    public HistoriesPetDaoImpl() {
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
    public List<HistoriesPet> buscarTodos() {
        List<HistoriesPet> historiesOfDB = new ArrayList<>();
        String sql = "SELECT * FROM HISTORY_PET";
        try(PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
               
                HistoriesPet hisPet = new HistoriesPet(null , null , null , null, null, null );
                hisPet.setIdHistory(resultSet.getInt("HISTORY_ID"));
                hisPet.setDateOfConsult(resultSet.getObject("DATE_OF_CONSULT", LocalDate.class));
                
                hisPet.setClient(clientsDao.buscarPorId(resultSet.getInt("CLIENT_ID")));
                hisPet.setPet(petDao.buscarPorId(resultSet.getInt("PET_ID")));
                hisPet.setVetDoctor(vetDao.buscarPorId(resultSet.getInt("VET_DOCTOR_ID")));

                hisPet.setDiagnostic(resultSet.getString("DIAGNOSTIC"));
                hisPet.setDescription(resultSet.getString("DESCRIPTION"));

                historiesOfDB.add(hisPet);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historiesOfDB;

        
    }

 

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM history_pet WHERE HISTORY_ID = ?";
        try(PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    

    @Override
    public void insertar(HistoriesPet hisPet) {
        String sql = "INSERT INTO history_pet (CLIENT_ID, PET_ID, VET_DOCTOR_ID, DESCRIPTION, DIAGNOSTIC, DATE_OF_CONSULT) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)){

            statement.setInt(1 , hisPet.getClient().getIdClient());
            statement.setInt(2 , hisPet.getPet().getIdPet());
            statement.setInt(3 , hisPet.getVetDoctor().getIdVetDoctor());
            statement.setString(4 , hisPet.getDescription());
            statement.setString(5 , hisPet.getDiagnostic());
            statement.setDate(6 , hisPet.changeType(hisPet.getDateOfConsult()));
            
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public void actualizar(HistoriesPet obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }


    @Override
    public HistoriesPet buscarPorId(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }
    
}
