package com.frgp.remember.Entidades;

public class ListaNegra {

    private int id_bloqueo;
    private Usuarios id_usuario;
    private Usuarios id_user_bloqueado;

    public ListaNegra() {
    }

    public ListaNegra(int id_bloqueo, Usuarios id_usuario, Usuarios id_user_bloqueado) {
        this.id_bloqueo = id_bloqueo;
        this.id_usuario = id_usuario;
        this.id_user_bloqueado = id_user_bloqueado;
    }


    public int getId_bloqueo() {
        return id_bloqueo;
    }

    public void setId_bloqueo(int id_bloqueo) {
        this.id_bloqueo = id_bloqueo;
    }

    public Usuarios getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuarios id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Usuarios getId_user_bloqueado() {
        return id_user_bloqueado;
    }

    public void setId_user_bloqueado(Usuarios id_user_bloqueado) {
        this.id_user_bloqueado = id_user_bloqueado;
    }
}
