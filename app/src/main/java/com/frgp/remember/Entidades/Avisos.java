package com.frgp.remember.Entidades;

import java.sql.Date;

public class Avisos {

    private int id_aviso;
    private Rutinas id_rutina;
    private java.sql.Date fecha;

    public Avisos() {
    }

    public Avisos(int id_aviso, Rutinas id_rutina, Date fecha) {
        this.id_aviso = id_aviso;
        this.id_rutina = id_rutina;
        this.fecha = fecha;
    }

    public int getId_aviso() {
        return id_aviso;
    }

    public void setId_aviso(int id_aviso) {
        this.id_aviso = id_aviso;
    }

    public Rutinas getId_rutina() {
        return id_rutina;
    }

    public void setId_rutina(Rutinas id_rutina) {
        this.id_rutina = id_rutina;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
