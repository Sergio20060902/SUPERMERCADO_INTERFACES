package com.mycompany.dao; 

import com.mycompany.app.Empleado; 
import com.mycompany.app.ConexionBD; 
import com.mycompany.app.ConexionBD;
import com.mycompany.app.Empleado;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpleadoDAO {

    public List<Empleado> listar() throws SQLException {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleados";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Empleado(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("dni"),
                    rs.getString("puesto"),
                    rs.getDouble("salario"),
                    rs.getBoolean("activo")
                ));
            }
        }
        return lista;
    }

    public void insertar(Empleado emp) throws SQLException {
        String sql = "INSERT INTO empleados (nombre, apellidos, dni, puesto, salario, activo) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getApellidos());
            ps.setString(3, emp.getDni());
            ps.setString(4, emp.getPuesto());
            ps.setDouble(5, emp.getSalario());
            ps.setBoolean(6, emp.isActivo());
            
            ps.executeUpdate();
        }
    }

    public void darDeBaja(int id) throws SQLException {
        String sql = "UPDATE empleados SET activo = false WHERE id = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}