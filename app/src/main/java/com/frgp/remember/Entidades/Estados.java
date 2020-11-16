package com.frgp.remember.Entidades;

public class Estados {

    private int id_estado;
    private String estado;

    public Estados(int id_estado, String estado) {
        this.id_estado = id_estado;
        this.estado = estado;
    }

    public Estados() {
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
