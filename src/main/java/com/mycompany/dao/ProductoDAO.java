package com.mycompany.dao;

import com.mycompany.app.ConexionBD;
import com.mycompany.app.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> listar() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                ));
            }
        }

        return lista;
    }

    public void actualizarStock(int idProducto, int nuevoStock) throws SQLException {
        String sql = "UPDATE productos SET stock = ? WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevoStock);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        }
    }
}
