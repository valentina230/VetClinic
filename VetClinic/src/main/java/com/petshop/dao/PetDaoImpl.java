package com.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.petshop.model.Pets;
import com.petshop.model.Sex;
import com.petshop.model.StatusPet;
import com.petshop.model.PetType;
import com.petshop.utils.ConexionPOSTGRES;

public class PetDaoImpl implements BaseDAO<Pets> {
    private Connection conexion;
    private ClientDaoImpl clientDao = new ClientDaoImpl();

    public PetDaoImpl() {
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
    public Pets buscarPorId(int id) {
        String sql = "SELECT * FROM pet WHERE PET_ID = ?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)){
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    Pets pet = new Pets();
                    pet.setIdPet(resultSet.getInt("PET_ID"));
                    pet.setName(resultSet.getString("PET_NAME"));
                    pet.setSpecies(PetType.valueOf(resultSet.getString("SPECIES").replace(" ", "_").toUpperCase()));
                    pet.setBreed(resultSet.getString("RACE"));
                    pet.setSex(Sex.valueOf(resultSet.getString("SEX").toUpperCase()));
                    pet.setAge(resultSet.getInt("YEAR_BORN"));
                    pet.setStatus(StatusPet.valueOf(resultSet.getString("STATUS")));
                    pet.setOwner(clientDao.buscarPorId(resultSet.getInt("ID_CLIENT")));
                    
                    return pet;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Pets> buscarTodos() {
        List<Pets> pets = new ArrayList<>();
        String sql = "SELECT * FROM pet";
        try(PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Pets pet = new Pets();
                pet.setIdPet(resultSet.getInt("PET_ID"));
                pet.setName(resultSet.getString("PET_NAME"));
                pet.setSpecies(PetType.valueOf(resultSet.getString("SPECIES").replace(" ", "_").toUpperCase()));
                pet.setBreed(resultSet.getString("RACE"));
                pet.setSex(Sex.valueOf(resultSet.getString("SEX").toUpperCase()));
                pet.setAge(resultSet.getInt("YEAR_BORN"));
                pet.setStatus(StatusPet.valueOf(resultSet.getString("STATUS")));
                pet.setOwner(clientDao.buscarPorId(resultSet.getInt("ID_CLIENT")));
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pets;
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM pet WHERE PET_ID = ?";
        try(PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    

    @Override
public void insertar(Pets pet) {
  
    String sql = "INSERT INTO pet (PET_NAME, SPECIES, RACE, SEX, YEAR_BORN, STATUS, ID_CLIENT) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement statement = conexion.prepareStatement(sql)) {
        statement.setString(1, pet.getName());
        statement.setString(2, pet.getSpecies().toString());
        statement.setString(3, pet.getBreed());
        statement.setString(4, pet.getSex().toString());
        statement.setInt(5, pet.getAge());
        statement.setString(6, pet.getStatus().toString());
        statement.setInt(7, pet.getOwner().getIdClient());
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    

    @Override
    public void actualizar(Pets pet) {

         String sql = "UPDATE pet SET  PET_NAME = ?, SPECIES = ?, RACE = ?, SEX = ?, YEAR_BORN = ?, STATUS = ? , ID_CLIENT= ? WHERE PET_ID = ?";
        try(PreparedStatement statement = conexion.prepareStatement(sql)) {
            
            statement.setString(1, pet.getName());
            statement.setString(2, pet.getSpecies().toString());
            statement.setString(3, pet.getBreed());
            
            statement.setString(4, pet.getSex().toString());
            statement.setInt(5, pet.getAge());
            statement.setString(6, pet.getStatus().toString());
            statement.setInt(7, pet.getOwner().getIdClient());
            statement.setInt(8, pet.getIdPet());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
}
