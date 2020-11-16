package com.frgp.remember.Entidades;

public class Recuperaciones {

    private int id_recuperacion;
    private Usuarios id_usuario;
    private String telefono;


    public Recuperaciones() {
    }

    public Recuperaciones(int id_recuperacion, Usuarios id_usuario, String telefono) {
        this.id_recuperacion = id_recuperacion;
        this.id_usuario = id_usuario;
        this.telefono = telefono;
    }

    public int getId_recuperacion() {
        return id_recuperacion;
    }

    public void setId_recuperacion(int id_recuperacion) {
        this.id_recuperacion = id_recuperacion;
    }

    public Usuarios getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuarios id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
