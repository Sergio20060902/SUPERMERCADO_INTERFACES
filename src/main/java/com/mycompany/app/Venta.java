package com.mycompany.app;

import java.time.LocalDateTime;

public class Venta {

    private int id;
    private LocalDateTime fecha;
    private double total;

    // 🔹 Constructor vacío
    public Venta() {
    }

    // 🔹 Constructor sin id (para nuevas ventas)
    public Venta(LocalDateTime fecha, double total) {
        this.fecha = fecha;
        this.total = total;
    }

    // 🔹 Constructor completo
    public Venta(int id, LocalDateTime fecha, double total) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
    }

    // 🔹 Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // 🔹 Método útil para mostrar la venta
    @Override
    public String toString() {
        return "Venta{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", total=" + total +
                '}';
    }
}
