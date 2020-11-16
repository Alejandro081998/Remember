package com.frgp.remember.Entidades;

import java.sql.Timestamp;

public class Notificaciones {

    private int idNotificacion;
    private Usuarios idRemitente;
    private Usuarios idDestinatario;
    private Timestamp hora;
    private String descripcion;
    private Apartados idApartado;
    private Estados estado;
    private Estados envio;

    public Notificaciones() {
    }

    public Notificaciones(int idNotificacion, Usuarios idRemitente, Usuarios idDestinatario, Timestamp hora, String descripcion, Apartados idApartado, Estados estado, Estados envio) {
        this.idNotificacion = idNotificacion;
        this.idRemitente = idRemitente;
        this.idDestinatario = idDestinatario;
        this.hora = hora;
        this.descripcion = descripcion;
        this.idApartado = idApartado;
        this.estado = estado;
        this.envio = envio;
    }

    public int getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Usuarios getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(Usuarios idRemitente) {
        this.idRemitente = idRemitente;
    }

    public Usuarios getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(Usuarios idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    public Timestamp getHora() {
        return hora;
    }

    public void setHora(Timestamp hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Apartados getIdApartado() {
        return idApartado;
    }

    public void setIdApartado(Apartados idApartado) {
        this.idApartado = idApartado;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public Estados getEnvio() {
        return envio;
    }

    public void setEnvio(Estados envio) {
        this.envio = envio;
    }
}
