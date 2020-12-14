package com.frgp.remember.Entidades;

import android.graphics.Bitmap;

public class Seguimiento {

    private Usuarios usuarios;
    private Bitmap imagen;

    public Seguimiento(){}

    public Seguimiento(Usuarios usuarios, Bitmap imagen) {
        this.usuarios = usuarios;
        this.imagen = imagen;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
