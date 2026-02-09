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

    //Listar todos los productos
    public List<Producto> listar() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        }

        return lista;
    }

    //Buscar producto por ID
    public Producto buscarPorId(int id) throws SQLException {
        Producto producto = null;
        String sql = "SELECT * FROM productos WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                producto = mapearProducto(rs);
            }
        }

        return producto;
    }

    //Insertar producto
    public void insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos (nombre, precio, stock) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());

            ps.executeUpdate();
        }
    }

    //Actualizar producto completo
    public void actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, stock = ? WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());
            ps.setInt(4, producto.getId());

            ps.executeUpdate();
        }
    }

    //Actualizar solo stock
    public void actualizarStock(int idProducto, int nuevoStock) throws SQLException {
        String sql = "UPDATE productos SET stock = ? WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevoStock);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        }
    }

    //Eliminar producto
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    //Mapper privado
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getInt("stock")
        );
    }
    
    public int getStockReal(int idProducto) {
        try {
            Producto p = buscarPorId(idProducto);
            if (p != null) {
                // Obtenemos cuántos llevamos ya en el carrito
                int enCarrito = com.mycompany.app.Carrito.getInstancia().getCantidadProducto(idProducto);
                
                // Devolvemos la resta (Stock BD - Stock Carrito)
                return p.getStock() - enCarrito;
            }
        } catch (SQLException e) {
            System.err.println("Error al calcular stock real: " + e.getMessage());
        }
        return 0; // Si falla o no existe, decimos que hay 0
    }

    // Devuelve solo el nombre (útil para pintar etiquetas rápido)
    public String getNombreProducto(int idProducto) {
        try {
            Producto p = buscarPorId(idProducto);
            if (p != null) return p.getNombre();
        } catch (SQLException e) {
            System.err.println("Error al obtener nombre: " + e.getMessage());
        }
        return "Desconocido";
    }
}
