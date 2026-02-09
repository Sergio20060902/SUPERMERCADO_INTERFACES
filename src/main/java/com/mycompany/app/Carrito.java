/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Studio Wuon Wuon
 */
public class Carrito {

    private static Carrito instancia;

    // ALMACENAMOS PRODUCTO Y CANTIDAD, CON ID = CLAVE DEL MAP
    private Map<Integer, ElementoCarrito> elementos;

    private Carrito() {
        elementos = new HashMap<>();
    }

    // OBTIENE LA INSTANCIA
    public static Carrito getInstancia() {
        if (instancia == null) {
            instancia = new Carrito();
        }
        return instancia;
    }

    public void anadirProducto(Producto producto, int cantidad) {
        if (producto == null || cantidad <= 0) {
            return;
        }

        int id = producto.getId();

        if (elementos.containsKey(id)) {

            ElementoCarrito existente = elementos.get(id);
            existente.setCantidad(existente.getCantidad() + cantidad);
        } else {

            elementos.put(id, new ElementoCarrito(producto, cantidad));
        }
        System.out.println("Añadido al carrito: " + producto.getNombre() + " x" + cantidad);
    }

    public void eliminarProducto(int idProducto) {
        elementos.remove(idProducto);
    }

    public void limpiarCarrito() {
        elementos.clear();
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ElementoCarrito item : elementos.values()) {
            total += item.getImporteTotal();
        }
        return total;
    }

    public List<ElementoCarrito> getListaElementos() {
        return new ArrayList<>(elementos.values());
    }

    public Map<Integer, Integer> getMapaParaDAO() {
        Map<Integer, Integer> mapaVenta = new HashMap<>();
        for (ElementoCarrito item : elementos.values()) {
            mapaVenta.put(item.getProducto().getId(), item.getCantidad());
        }
        return mapaVenta;
    }

    // CLASE INTERNA PARA GUARDAR EL PRODUCTO CON LA CANTIDAD
    public class ElementoCarrito {

        private Producto producto;
        private int cantidad;

        public ElementoCarrito(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
        }

        public Producto getProducto() {
            return producto;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }

        // Calcula el precio total de esta línea (Precio unitario * Cantidad)
        public double getImporteTotal() {
            return producto.getPrecio() * cantidad;
        }

        @Override
        public String toString() {
            return producto.getNombre() + " x" + cantidad + " = " + getImporteTotal() + "€";
        }
    }

    //cuantas unidades de un producto llevamos ya en el carrito
    public int getCantidadProducto(int idProducto) {
        if (elementos.containsKey(idProducto)) {
            return elementos.get(idProducto).getCantidad();
        }
        return 0;
    }
    // Poner en Carrito.java

    public void vaciarCarrito() {
        elementos.clear();
    }
}
