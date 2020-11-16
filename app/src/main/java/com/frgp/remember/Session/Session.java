package com.frgp.remember.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private int id_usuario;
    private String tipo_rol;
    private String contrasena;
    private String usuario;
    private Context ct;

    public Session(int id_usuario, String tipo_rol, String contra, String usuario, Context ct) {
        this.id_usuario = id_usuario;
        this.tipo_rol = tipo_rol;
        this.contrasena = contra;
        this.usuario = usuario;
        this.ct = ct;
    }

    public Session(){
    }

    public void nueva_session(){

        SharedPreferences preferencias = ct.getSharedPreferences("session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("id_session",id_usuario);
        editor.putString("tiporol_session",tipo_rol);
        editor.putString("contrasena",contrasena);
        editor.putString("usuario",usuario);
        editor.apply();

    }

    public void cargar_session(){

        SharedPreferences preferences = ct.getSharedPreferences("session", Context.MODE_PRIVATE);
        this.id_usuario = preferences.getInt("id_session",-1);
        this.tipo_rol = preferences.getString("tiporol_session","");
        this.contrasena = preferences.getString("contrasena","");
        this.usuario = preferences.getString("usuario","");

    }

    public void cerrar_session(){

        SharedPreferences preferencias = ct.getSharedPreferences("session", Context.MODE_PRIVATE);
        this.contrasena = "";
        SharedPreferences.Editor editor = preferencias.edit();
        editor.clear();
        editor.apply();

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Context getCt() {
        return ct;
    }

    public void setCt(Context ct) {
        this.ct = ct;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTipo_rol() {
        return tipo_rol;
    }

    public void setTipo_rol(String tipo_rol) {
        this.tipo_rol = tipo_rol;
    }
}
