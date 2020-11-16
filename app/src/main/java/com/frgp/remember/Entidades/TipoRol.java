package com.frgp.remember.Entidades;

public class TipoRol {

    private int id_rol;
    private String rol;

    public TipoRol(int id_rol, String rol) {
        this.id_rol = id_rol;
        this.rol = rol;
    }

    public TipoRol() {
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
