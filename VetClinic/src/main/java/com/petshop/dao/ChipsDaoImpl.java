package com.petshop.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.petshop.model.Chips;
import com.petshop.model.StatusChip;
import com.petshop.model.Technology;
import com.petshop.utils.ConexionPOSTGRES;

public class ChipsDaoImpl implements BaseDAO<Chips>{
    private Connection conexion;
    private PetDaoImpl petsDao = new PetDaoImpl();

    public ChipsDaoImpl() {
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
    public void insertar(Chips chip) {
        String sql = "INSERT INTO chip (FACTORY_ID, TECHNOLOGY, STATUS, DATE_INSTALATION, EXPIRATION_DATE, PET_ID) VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, chip.getIdFactory());
            statement.setString(2, chip.getTechnology().toString());
            statement.setString(3, chip.getStatus().toString());
            statement.setDate(4, chip.changeType(chip.getDateOfInstalation()));
            statement.setDate(5, null);
            statement.setInt(6, chip.getPet().getIdPet());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   

    @Override
    public Chips buscarPorId(int id) {
        String sql = "SELECT * FROM chip WHERE CHIP_ID = ?";
        try(PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Chips chip = new Chips();
                    chip.setIdChip(resultSet.getInt("CHIP_ID"));
                    chip.setIdFactory(resultSet.getInt("FACTORY_ID"));
                    chip.setTechnology(Technology.valueOf(resultSet.getString("TECHNOLOGY").replace(" ", "_").toUpperCase()));
                    chip.setStatus(StatusChip.valueOf(resultSet.getString("STATUS").replace(" ", "_").toUpperCase()));
                    chip.setDateOfInstalation(resultSet.getDate("DATE_INSTALATION").toLocalDate());
                    chip.setExpirationDate(resultSet.getDate("EXPIRATION_DATE").toLocalDate());
                    chip.setPet(petsDao.buscarPorId(resultSet.getInt("PET_ID")));
                    return chip;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Chips> buscarTodos() {
        String sql = "SELECT * FROM chip";
        List<Chips> chipsOfDb = new ArrayList<>();
        try(PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Chips chip = new Chips();
                    chip.setIdChip(resultSet.getInt("CHIP_ID"));
                    chip.setIdFactory(resultSet.getInt("FACTORY_ID"));
                    chip.setTechnology(Technology.valueOf(resultSet.getString("TECHNOLOGY").replace(" ", "_")));
                    chip.setStatus(StatusChip.valueOf(resultSet.getString("STATUS").replace(" ", "_").toUpperCase()));
                    chip.setDateOfInstalation(resultSet.getDate("DATE_INSTALATION").toLocalDate());
                    if (resultSet.getDate("EXPIRATION_DATE") != null) {
                        chip.setExpirationDate(resultSet.getDate("EXPIRATION_DATE").toLocalDate());
                    } else{
                        chip.setExpirationDate(null);
                    }
                    
                    chip.setPet(petsDao.buscarPorId(resultSet.getInt("PET_ID")));
                    chipsOfDb.add(chip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chipsOfDb;
    }

    @Override
    public void actualizar(Chips chip) {
        String sql = "UPDATE chip SET FACTORY_ID = ?, TECHNOLOGY = ?, STATUS = ?, DATE_INSTALATION = ?, EXPIRATION_DATE = ? WHERE CHIP_ID = ?";
        try(PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, chip.getIdFactory());
            statement.setString(2, chip.getTechnology().toString());
            statement.setString(3, chip.getStatus().toString());
            
            statement.setDate(4, Date.valueOf(chip.getDateOfInstalation()));
            if (chip.getExpirationDate() != null) {
                statement.setDate(5, Date.valueOf(chip.getExpirationDate()));
            } else{
                statement.setDate(5, null);
            }
            statement.setInt(6, chip.getIdChip());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM chip WHERE CHIP_ID = ?";
        try(PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}