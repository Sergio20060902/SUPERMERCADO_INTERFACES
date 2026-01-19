package com.mycompany.proyecto_supermercado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Configuración de tu base de datos (cámbiala si es diferente)
    private static final String URL = "jdbc:mysql://localhost:3306/hospital?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "123456"; // Pon aquí tu contraseña real de MySQL

    // Método que devuelve la conexión
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

    // ¡¡IMPORTANTE!! Añade este main para poder probar la conexión rápido
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