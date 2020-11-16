package com.frgp.remember.Entidades;


import java.io.Serializable;

public class SeguimientoDatos implements Serializable {

    private int Id;
    private String Titulo;
    private String Detalle;
    private int Imagen;

    public int getId() {
        return Id;
    }

    public SeguimientoDatos(int id, String titulo, String detalle, int imagen) {
        Id = id;
        Titulo = titulo;
        Detalle = detalle;
        Imagen = imagen;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDetalle() {
        return Detalle;
    }

    public void setDetalle(String detalle) {
        Detalle = detalle;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }



}
