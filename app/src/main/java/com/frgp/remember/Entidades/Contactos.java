package com.frgp.remember.Entidades;

public class Contactos {

    private int id_contacto;
    private Pacientes id_paciente;
    private String nombre_contacto;
    private String numero;
    private boolean estado;

    public Contactos() {
    }

    public Contactos(int id_contacto, Pacientes id_paciente, String nombre_contacto, String numero, boolean estado) {
        this.id_contacto = id_contacto;
        this.id_paciente = id_paciente;
        this.nombre_contacto = nombre_contacto;
        this.numero = numero;
        this.estado = estado;
    }

    public int getId_contacto() {
        return id_contacto;
    }

    public void setId_contacto(int id_contacto) {
        this.id_contacto = id_contacto;
    }

    public Pacientes getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(Pacientes id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getNombre_contacto() {
        return nombre_contacto;
    }

    public void setNombre_contacto(String nombre_contacto) {
        this.nombre_contacto = nombre_contacto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
