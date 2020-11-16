package com.frgp.remember.Entidades;

import java.sql.Time;
import java.time.LocalTime;

public class Rutinas {

    private int id_rutina;
    private Usuarios id_paciente;
    private Usuarios id_creador;
    private Time hora;
    private TipoRutinas tipo_rutina;
    private String Descripcion;
    private Estados estados;

    public Rutinas() {
    }

    public Rutinas(int id_rutina, Usuarios id_paciente, Usuarios id_creador, TipoRutinas tipo_rutina, String descripcion, Estados estados, Time hora) {
        this.id_rutina = id_rutina;
        this.id_paciente = id_paciente;
        this.id_creador = id_creador;
        this.tipo_rutina = tipo_rutina;
        Descripcion = descripcion;
        this.estados = estados;
        this.hora = hora;
    }

    public int getId_rutina() {
        return id_rutina;
    }

    public void setId_rutina(int id_rutina) {
        this.id_rutina = id_rutina;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Usuarios getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(Usuarios id_paciente) {
        this.id_paciente = id_paciente;
    }

    public Usuarios getId_creador() {
        return id_creador;
    }

    public void setId_creador(Usuarios id_creador) {
        this.id_creador = id_creador;
    }

    public TipoRutinas getTipo_rutina() {
        return tipo_rutina;
    }

    public void setTipo_rutina(TipoRutinas tipo_rutina) {
        this.tipo_rutina = tipo_rutina;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Estados getEstados() {
        return estados;
    }

    public void setEstados(Estados estados) {
        this.estados = estados;
    }
}
