package com.mycompany.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Configuración de tu base de datos (cámbiala si es diferente)
    private static final String URL = "jdbc:mysql://localhost:3306/supermercado?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root"; 

   
    public static Connection getConexion() {
        Connection con = null;
        try {
            // Driver moderno de MySQL 8+
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("¡Conexión exitosa a la base de datos!");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver no encontrado. Añade mysql-connector-j-8.x.x.jar");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }

    
    public static void main(String[] args) {
        Connection prueba = getConexion();
        if (prueba != null) {
            try {
                prueba.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}