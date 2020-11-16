package com.frgp.remember.Entidades;

public class TipoRutinas {


    private int id_tipo_rutina;
    private String descripcion;
    private Estados estado;

    public TipoRutinas() {
    }

    public TipoRutinas(int id_tipo_rutina, String descripcion, Estados estado) {
        this.id_tipo_rutina = id_tipo_rutina;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getId_tipo_rutina() {
        return id_tipo_rutina;
    }

    public void setId_tipo_rutina(int id_tipo_rutina) {
        this.id_tipo_rutina = id_tipo_rutina;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }
}
