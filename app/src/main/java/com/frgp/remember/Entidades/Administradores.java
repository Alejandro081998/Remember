package com.frgp.remember.Entidades;

public class Administradores {

    private int id_admin;
    private Usuarios id_usuario;
    private Estados id_estado;

    public Administradores(int id_admin, Usuarios id_usuario, Estados id_estado) {
        this.id_admin = id_admin;
        this.id_usuario = id_usuario;
        this.id_estado = id_estado;
    }

    public Administradores() {
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public Usuarios getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuarios id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Estados getId_estado() {
        return id_estado;
    }

    public void setId_estado(Estados id_estado) {
        this.id_estado = id_estado;
    }
}
