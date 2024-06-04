package com.petshop.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionPOSTGRES {
    private static final String URL = "jdbc:postgresql://localhost:5432/db_petshop";
    private static final String USERNAME = "postgres"; // Nombre de usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos
    private static Connection conexion = null; // Instancia única de la conexión

    // Método privado para crear la conexión si no existe
    private static void crearConexion() throws SQLException {
        // Verificar si la conexión no existe o está cerrada
        if (conexion == null || conexion.isClosed()) {
            // Establecer una nueva conexión usando DriverManager de JDBC
            conexion = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
    }

    // Método estático para obtener la conexión (singleton)
    public static Connection obtenerConexion() throws SQLException {
        // Verificar si la conexión no existe o está cerrada
        if (conexion == null || conexion.isClosed()) {
            crearConexion(); // Crear la conexión si no existe
        }
        return conexion; // Devolver la instancia de la conexión
    }

    // Método estático para cerrar la conexión y recursos relacionados
    public static void cerrarConexion() {
        try {
            // Verificar si la conexión no es nula y no está cerrada
            if (conexion != null && !conexion.isClosed()) {
                conexion.close(); // Cerrar la conexión
                System.out.println("Conexión cerrada correctamente."); // Mensaje de confirmación
            }
        } catch (SQLException e) {
            // Capturar y manejar cualquier excepción SQL al cerrar la conexión
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
            e.printStackTrace(); // Imprimir el rastreo de la excepción
        }
    }
}
