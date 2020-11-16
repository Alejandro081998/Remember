package com.frgp.remember.Entidades;

public class Familiares {

    private int id_familiar;
    private Usuarios id_usuario;
    private String parentesco;

    public Familiares(int id_familiar, Usuarios id_usuario, String parentesco) {
        this.id_familiar = id_familiar;
        this.id_usuario = id_usuario;
        this.parentesco = parentesco;
    }

    public Familiares() {
    }

    public int getId_familiar() {
        return id_familiar;
    }

    public void setId_familiar(int id_familiar) {
        this.id_familiar = id_familiar;
    }

    public Usuarios getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuarios id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}
