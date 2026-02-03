package com.mycompany.dao;

import com.mycompany.app.ConexionBD;
import com.mycompany.app.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    // 📋 Listar todos los empleados
    public List<Empleado> listar() throws SQLException {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleados";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearEmpleado(rs));
            }
        }
        return lista;
    }

    // 🔍 Buscar empleado por ID
    public Empleado buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM empleados WHERE id = ?";
        Empleado emp = null;

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emp = mapearEmpleado(rs);
                }
            }
        }
        return emp;
    }

    // ➕ Insertar empleado
    public void insertar(Empleado emp) throws SQLException {

        String sql = "INSERT INTO empleados (nombre, apellidos, dni, salario) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getApellidos());
            ps.setString(3, emp.getDni());
            ps.setDouble(4, emp.getSalario());

            ps.executeUpdate();
        }
    }

    // ✏ Actualizar empleado
    public void actualizar(Empleado emp) throws SQLException {

        String sql = "UPDATE empleados SET nombre = ?, apellidos = ?, dni = ?, salario = ? WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getNombre());
            ps.setString(2, emp.getApellidos());
            ps.setString(3, emp.getDni());
            ps.setDouble(4, emp.getSalario());
            ps.setInt(5, emp.getId());

            ps.executeUpdate();
        }
    }

    // 🧠 Mapper privado
    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        return new Empleado(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("dni"),
                rs.getDouble("salario")
        );
    }
}
