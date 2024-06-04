package com.petshop.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.petshop.dao.BaseDAO;
import com.petshop.dao.ChipsDaoImpl;
import com.petshop.dao.ClientDaoImpl;
import com.petshop.dao.HistoriesPetDaoImpl;
import com.petshop.dao.PetDaoImpl;
import com.petshop.dao.VetDoctorsDaoImpl;
import com.petshop.model.Chips;
import com.petshop.model.Clients;
import com.petshop.model.HistoriesPet;
import com.petshop.model.Pets;
import com.petshop.model.VetDoctors;
import com.petshop.utils.ConexionPOSTGRES;

import javafx.collections.ObservableList;



public abstract class BaseController {

    protected BaseDAO<Clients> clientDao; 
    protected BaseDAO<HistoriesPet> historiesDao;
    protected BaseDAO<Pets> petsDao;
    protected BaseDAO<Chips> chipDao;
    protected BaseDAO<VetDoctors> vetDoctorsDao;
    
    protected Connection conexion; // Conexión a la base de datos
    

    public BaseController() {
        this.clientDao = new ClientDaoImpl();
        this.petsDao = new PetDaoImpl();
        this.chipDao = new ChipsDaoImpl();
        this.historiesDao = new HistoriesPetDaoImpl();
        this.vetDoctorsDao = new VetDoctorsDaoImpl();
    }

    

    protected void abrirConexion() {
        try {
            conexion = ConexionPOSTGRES.obtenerConexion(); // Obtiene la conexión desde la clase mejorada
            System.out.println("Conexión establecida correctamente."); // Mensaje de confirmación
        } catch (SQLException e) {
            System.err.println("Error al abrir la conexión: " + e.getMessage()); // Mensaje de error
            e.printStackTrace(); // Imprime el rastreo de la excepción
        }
    }

    // Método para cerrar la conexión a la base de datos
    protected void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close(); // Cierra la conexión si no está cerrada
                System.out.println("Conexión cerrada correctamente."); // Mensaje de confirmación
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage()); // Mensaje de error
            e.printStackTrace(); // Imprime el rastreo de la excepción
        }
    }

    // Método para cargar datos de autores desde la base de datos a una lista observable
    protected void uploadDataClients(ObservableList<Clients> clientsData) {
        clientsData.clear(); // Borra los datos existentes en la lista observable

        // Obtiene la lista de autores desde la base de datos utilizando el DAO
        List<Clients> clientsOfDb = clientDao.buscarTodos();
        clientsData.addAll(clientsOfDb); // Agrega los autores a la lista observable
    }
    
    protected void uploadDataPets(ObservableList<Pets> petsData) {
        petsData.clear(); // Borra los datos existentes en la lista observable

        // Obtiene la lista de autores desde la base de datos utilizando el DAO
        List<Pets> petsOfDb = petsDao.buscarTodos();
        petsData.addAll(petsOfDb); // Agrega los autores a la lista observable
    }
    
    protected void uploadDataChips(ObservableList<Chips> chipsData) {
        chipsData.clear(); // Borra los datos existentes en la lista observable

        // Obtiene la lista de autores desde la base de datos utilizando el DAO
        List<Chips> chipsOfDB = chipDao.buscarTodos();
        chipsData.addAll(chipsOfDB); // Agrega los autores a la lista observable
    }

    protected void uploadDataHistories(ObservableList<HistoriesPet> historiesData) {
        historiesData.clear(); // Borra los datos existentes en la lista observable

        // Obtiene la lista de historiales desde la base de datos utilizando el DAO
        List<HistoriesPet> historiesOfDB = historiesDao.buscarTodos();
        historiesData.addAll(historiesOfDB); // Agrega los autores a la lista observable
    }

    protected void uploadDataVet(ObservableList<VetDoctors> vetData) {
        vetData.clear(); // Borra los datos existentes en la lista observable

        // Obtiene la lista de veterinarios desde la base de datos utilizando el DAO
        List<VetDoctors> vetOfDB = vetDoctorsDao.buscarTodos();
        vetData.addAll(vetOfDB); // Agrega los veterinarios a la lista observable
    }
}
    

