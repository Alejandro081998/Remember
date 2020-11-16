package com.frgp.remember.Entidades;

public class Dias {

    private int id_dia;
    private String Descripcion;
    private Estados estado;

    public Dias() {
    }

    public Dias(int id_dia, String descripcion, Estados estado) {
        this.id_dia = id_dia;
        Descripcion = descripcion;
        this.estado = estado;
    }

    public int getId_dia() {
        return id_dia;
    }

    public void setId_dia(int id_dia) {
        this.id_dia = id_dia;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }
}
