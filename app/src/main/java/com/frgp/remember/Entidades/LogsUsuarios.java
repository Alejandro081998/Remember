package com.frgp.remember.Entidades;

import java.sql.Timestamp;

public class LogsUsuarios {
    private int id_usuario;
    private String logdesc;
    private Timestamp fecha_hora_log;

    public Timestamp getFecha_hora_log() {
        return fecha_hora_log;
    }

    public void setFecha_hora_log(Timestamp fecha_hora_log) {
        this.fecha_hora_log = fecha_hora_log;
    }



    public LogsUsuarios(int id_usuario, String logdesc, Timestamp fhlog) {
        this.id_usuario = id_usuario;
        this.logdesc = logdesc;
        this.fecha_hora_log = fhlog;
    }



    public LogsUsuarios() {

    }


    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getLogdesc() {
        return logdesc;
    }

    public void setLogdesc(String logdesc) {
        this.logdesc = logdesc;
    }

    @Override
    public String toString(){
        return  this.logdesc;

    }

}