package com.frgp.remember.Entidades;

public class Vinculaciones {

    private int id_vinculacion;
    private Usuarios id_supervisor;
    private Usuarios id_paciente;
    private Estados estado_solicitud;
    private Estados estado_vinculacion;

    public Vinculaciones(){}

    public Vinculaciones(int id_vinculacion, Usuarios id_supervisor, Usuarios id_paciente, Estados estado_solicitud, Estados estado_vinculacion) {
        this.id_vinculacion = id_vinculacion;
        this.id_supervisor = id_supervisor;
        this.id_paciente = id_paciente;
        this.estado_solicitud = estado_solicitud;
        this.estado_vinculacion = estado_vinculacion;
    }

    public int getId_vinculacion() {
        return id_vinculacion;
    }

    public void setId_vinculacion(int id_vinculacion) {
        this.id_vinculacion = id_vinculacion;
    }

    public Usuarios getId_supervisor() {
        return id_supervisor;
    }

    public void setId_supervisor(Usuarios id_supervisor) {
        this.id_supervisor = id_supervisor;
    }

    public Usuarios getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(Usuarios id_paciente) {
        this.id_paciente = id_paciente;
    }

    public Estados getEstado_solicitud() {
        return estado_solicitud;
    }

    public void setEstado_solicitud(Estados estado_solicitud) {
        this.estado_solicitud = estado_solicitud;
    }

    public Estados getEstado_vinculacion() {
        return estado_vinculacion;
    }

    public void setEstado_vinculacion(Estados estado_vinculacion) {
        this.estado_vinculacion = estado_vinculacion;
    }
}
