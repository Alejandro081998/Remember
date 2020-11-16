package com.frgp.remember.Entidades;

public class Pacientes {

    private int id_paciente;
    private Usuarios id_usuario;
    private String factorS;
    private Float peso;
    private Float altura;
    private String medico_cabecera;

    public Pacientes(int id_paciente, Usuarios id_usuario, String factorS,
                     Float peso, Float altura, String medicocabecera) {
        this.id_paciente = id_paciente;
        this.id_usuario = id_usuario;
        this.factorS = factorS;
        this.peso = peso;
        this.altura = altura;
        this.medico_cabecera = medicocabecera;
    }

    public Pacientes() {
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public Usuarios getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuarios id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFactorS() {
        return factorS;
    }

    public void setFactorS(String factorS) {
        this.factorS = factorS;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public String getMedico_cabecera() {
        return medico_cabecera;
    }

    public void setMedico_cabecera(String medico_cabecera) {
        this.medico_cabecera = medico_cabecera;
    }
}
