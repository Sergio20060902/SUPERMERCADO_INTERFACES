package com.mycompany.app;

/**
 * Clase que representa el modelo de datos de un Empleado.
 */
public class Empleado {
    private int id;
    private String nombre;
    private String apellidos;
    private String dni;
    
    private double salario;
    

    public Empleado(int aInt, String string, String string1, String string2, double aDouble) {}

    public Empleado(int id, String nombre, String apellidos, String dni, String puesto, double salario, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        
        this.salario = salario;
       
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", salario=" + salario + '}';
    }
   

    
}