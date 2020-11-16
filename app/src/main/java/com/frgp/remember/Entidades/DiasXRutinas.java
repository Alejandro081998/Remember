package com.frgp.remember.Entidades;

public class DiasXRutinas {

    private Dias id_dia;
    private Rutinas id_rutina;
    private Estados estado;

    public DiasXRutinas() {
    }

    public DiasXRutinas(Dias id_dia, Rutinas id_rutina, Estados estado) {
        this.id_dia = id_dia;
        this.id_rutina = id_rutina;
        this.estado = estado;
    }

    public Dias getId_dia() {
        return id_dia;
    }

    public void setId_dia(Dias id_dia) {
        this.id_dia = id_dia;
    }

    public Rutinas getId_rutina() {
        return id_rutina;
    }

    public void setId_rutina(Rutinas id_rutina) {
        this.id_rutina = id_rutina;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }
}
