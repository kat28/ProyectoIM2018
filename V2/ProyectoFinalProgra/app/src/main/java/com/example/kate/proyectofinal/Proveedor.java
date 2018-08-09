package com.example.kate.proyectofinal;

public class Proveedor {
    private String IdProveedor;
    private String Nombre;
    private String Direccion;
    private String Telefono;

    public Proveedor() {
    }

    public String getIdProveedor() {
        return IdProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        IdProveedor = idProveedor;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
}
