package com.frgp.remember.Entidades;

import java.sql.Blob;
import java.util.Date;

public class Usuarios {

    private int id_usuario;
    private String usuario;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String dni;
    private char sexo;
    private String mail;
    private java.sql.Date fecha_nacimiento;
    private java.sql.Date fecha_alta;
    private TipoRol tipo;
    private Estados estado;
    private Blob imagen;

    public Usuarios(int id_usuario, String usuario, String contrasena,
                    String nombre, String apellido, String dni, char sexo,
                    String mail, java.sql.Date fecha_nacimiento, java.sql.Date fecha_alta, TipoRol tipo,
                    Estados estado, Blob imagen) {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.sexo = sexo;
        this.mail = mail;
        this.fecha_nacimiento = fecha_nacimiento;
        this.fecha_alta = fecha_alta;
        this.tipo = tipo;
        this.estado = estado;
        this.imagen = imagen;
    }

    public Usuarios() {
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public java.sql.Date getFecha_nacimiento() {
        return  fecha_nacimiento;
    }

    public void setFecha_nacimiento(java.sql.Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public java.sql.Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(java.sql.Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public TipoRol getTipo() {
        return tipo;
    }

    public void setTipo(TipoRol tipo) {
        this.tipo = tipo;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }
}
