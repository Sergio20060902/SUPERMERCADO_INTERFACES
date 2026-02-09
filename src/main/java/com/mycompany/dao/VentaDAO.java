package com.mycompany.dao;

import com.mycompany.app.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Map;

public class VentaDAO {

    /**
     * Registra una venta en la base de datos
     * @param productosVendidos Map<idProducto, cantidad>
     * @param total Total de la venta
     */
    public void registrarVenta(Map<Integer, Integer> productosVendidos, double total) throws SQLException {

        String sqlVenta = "INSERT INTO ventas (fecha, total) VALUES (?, ?)";
        String sqlDetalle = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio) " +
                            "VALUES (?, ?, ?, (SELECT precio FROM productos WHERE id = ?))";
        String sqlStock = "UPDATE productos SET stock = stock - ? WHERE id = ?";

        Connection con = null;

        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false); // 🔒 transacción

            // 1️⃣ Insertar venta
            int idVenta;
            try (PreparedStatement psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
                psVenta.setObject(1, LocalDateTime.now());
                psVenta.setDouble(2, total);
                psVenta.executeUpdate();

                ResultSet rs = psVenta.getGeneratedKeys();
                if (!rs.next()) {
                    throw new SQLException("No se pudo obtener el ID de la venta");
                }
                idVenta = rs.getInt(1);
            }

            // 2️⃣ Insertar detalle de venta y actualizar stock
            for (Map.Entry<Integer, Integer> entry : productosVendidos.entrySet()) {
                int idProducto = entry.getKey();
                int cantidad = entry.getValue();

                // Detalle venta
                try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle)) {
                    psDetalle.setInt(1, idVenta);
                    psDetalle.setInt(2, idProducto);
                    psDetalle.setInt(3, cantidad);            
                    psDetalle.executeUpdate();
                }

                // Actualizar stock
                try (PreparedStatement psStock = con.prepareStatement(sqlStock)) {
                    psStock.setInt(1, cantidad);
                    psStock.setInt(2, idProducto);
                    psStock.executeUpdate();
                }
            }

            con.commit(); // ✅ todo OK

        } catch (SQLException e) {
            if (con != null) con.rollback(); // ❌ error → deshacer todo
            throw e;
        } finally {
            if (con != null) con.setAutoCommit(true);
        }
    }
}
