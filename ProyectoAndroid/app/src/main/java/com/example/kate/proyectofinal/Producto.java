package com.example.kate.proyectofinal;

public class Producto {
    private String IdProducto;
    private String nombre;
    private String precio;
    private String cantidad;

    public Producto() {
    }

    public String getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(String id) {
        this.IdProducto = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

}
