package com.frgp.remember.Entidades;

public class Profesionales {

    private int id_profesional;
    private Usuarios id_usuario;
    private String nro_matricula;

    public Profesionales(int id_profesional, Usuarios id_usuario, String nro_matricula) {
        this.id_profesional = id_profesional;
        this.id_usuario = id_usuario;
        this.nro_matricula = nro_matricula;
    }

    public Profesionales() {
    }

    public int getId_profesional() {
        return id_profesional;
    }

    public void setId_profesional(int id_profesional) {
        this.id_profesional = id_profesional;
    }

    public Usuarios getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuarios id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNro_matricula() {
        return nro_matricula;
    }

    public void setNro_matricula(String nro_matricula) {
        this.nro_matricula = nro_matricula;
    }
}
