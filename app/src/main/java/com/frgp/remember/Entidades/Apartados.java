package com.frgp.remember.Entidades;

public class Apartados {

    private int idApartado;
    private String descripcion;


    public Apartados() {
    }

    public Apartados(int idApartado, String descripcion) {
        this.idApartado = idApartado;
        this.descripcion = descripcion;
    }

    public int getIdApartado() {
        return idApartado;
    }

    public void setIdApartado(int idApartado) {
        this.idApartado = idApartado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
